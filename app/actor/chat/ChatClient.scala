package actor.chat

import akka.actor.{ActorRef, PoisonPill}
import repositories.ChatRepository

import scala.collection.mutable

class ChatClient(
                  val client: ActorRef,
                  chatRepository: ChatRepository
                ) extends ChatManagement with SessionManagement with ChatServer with KeepAlive {
  override def sessions: mutable.Map[String, ActorRef] = chatRepository.sessions

  override def unhandled(message: Any): Unit = {
    println(s"Unhandled: $message")
  }

  override def aroundPostStop(): Unit = {
    client ! PoisonPill
    sessions.find(_._2 == client).foreach {
      case (name, _) =>
        sessions -= name
        println("Logout " + name)
    }
    super.aroundPostStop()
  }
}
