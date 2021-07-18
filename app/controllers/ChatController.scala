package controllers

import actor.chat.ChatClient
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import com.google.inject.{Inject, Singleton}
import models.chat.{ChatMessage, Event}
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import repositories.ChatRepository
import views.html.ChatView

import scala.concurrent.ExecutionContext



@Singleton
class ChatController @Inject()(
                                      val controllerComponents: ControllerComponents,
                                      chatView: ChatView
                                    )(implicit executionContext: ExecutionContext,
                                      mat: Materializer,
                                      actorSystem: ActorSystem) extends BaseController {
  def chatSocket: WebSocket = WebSocket.accept[Event, ChatMessage] { _ =>
    ActorFlow.actorRef(clientActor => Props(new ChatClient(clientActor)))
  }

  def view(name: String): Action[AnyContent] = Action {
    implicit req =>
      println(ChatRepository.sessions)
      Ok(chatView(name, ChatRepository.sessions.keys.toList.filterNot(_ == name)))
  }
}
