package controllers

import actor.chat.ChatClient
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import com.google.inject.{Inject, Singleton}
import models.chat.{Event, OutgoingEvent}
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import repositories.ChatRepository

import scala.concurrent.ExecutionContext

@Singleton
class ChatController @Inject()(
                                val controllerComponents: ControllerComponents,
                                chatRepository: ChatRepository
                              )(implicit executionContext: ExecutionContext,
                                mat: Materializer,
                                actorSystem: ActorSystem) extends BaseController {
  def chatSocket: WebSocket = WebSocket.accept[Event, OutgoingEvent] { _ =>
    ActorFlow.actorRef { clientActor =>
      Props(new ChatClient(clientActor, chatRepository))
    }
  }
}
