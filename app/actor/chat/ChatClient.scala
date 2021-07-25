package actor.chat

import akka.actor.{ActorRef, PoisonPill}
import play.api.Logger
import repositories.ChatRepository

import scala.collection.mutable

class ChatClient(
                  val client: ActorRef,
                  chatRepository: ChatRepository
                ) extends ChatManagement with SessionManagement with ChatServer with KeepAlive {
  val logger: Logger = Logger(this.getClass)

  override def sessions: mutable.Map[String, ActorRef] = chatRepository.sessions

  override def unhandled(message: Any): Unit = {
    logger.error(s"Unhandled message: $message")
  }

  override def aroundPostStop(): Unit = {
    client ! PoisonPill
    sessions.find(_._2 == client).foreach {
      case (name, _) =>
        sessions -= name
    }
    super.aroundPostStop()
  }
}
