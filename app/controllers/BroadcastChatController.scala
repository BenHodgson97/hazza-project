package controllers

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, MergeHub, Source}
import com.google.inject.Inject
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, WebSocket}
import views.html.ChatView

import scala.concurrent.{ExecutionContext, Future}

class BroadcastChatController @Inject()(
                                         val controllerComponents: ControllerComponents,
                                         chatView: ChatView
                                       )(implicit executionContext: ExecutionContext,
                                         mat: Materializer,
                                         actorSystem: ActorSystem) extends BaseController {

  private type WSMessage = String

  private val (chatSink, chatSource) = {
    val source = MergeHub.source[WSMessage]
      .log("source")
      // Let's also do some input sanitization to avoid XSS attacks - just make a sanitizer
      .recoverWithRetries(-1, { case _: Exception => Source.empty })

    val sink = BroadcastHub.sink[WSMessage]
    source.toMat(sink)(Keep.both).run()
  }

  private val messageFlow: Flow[WSMessage, WSMessage, NotUsed] =
    Flow.fromSinkAndSource(chatSink, chatSource)

  def chatSocket: WebSocket = WebSocket.acceptOrResult[String, String] { _ =>
    Future.successful(messageFlow).map {
      Right(_)
    }
  }

  def view: Action[AnyContent] = Action {
    implicit req => Ok(chatView("dane", List()))
  }

}
