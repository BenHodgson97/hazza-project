package controllers

import actor.chat.ChatClient
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import com.google.inject.{Inject, Singleton}
import models.chat.{ChatMessage, Event}
import org.joda.time.DateTime
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import repositories.ChatRepository
import views.html.ChatView

import scala.concurrent.ExecutionContext

@Singleton
class ChatController @Inject()(
                                val controllerComponents: ControllerComponents,
                                chatView: ChatView,
                                chatRepository: ChatRepository
                              )(implicit executionContext: ExecutionContext,
                                mat: Materializer,
                                actorSystem: ActorSystem) extends BaseController {
  def chatSocket: WebSocket = WebSocket.accept[Event, Event] { _ =>
    ActorFlow.actorRef { clientActor =>
      Props(new ChatClient(clientActor, chatRepository))
    }
  }

  def view(name: String): Action[AnyContent] = Action {
    implicit req =>
      Ok(chatView(name, chatRepository.sessions.keys.toList.filterNot(_ == name)))
  }
}
