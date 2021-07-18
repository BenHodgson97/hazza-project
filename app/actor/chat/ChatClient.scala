package actor.chat

import akka.actor.{Actor, ActorRef}
import repositories.ChatRepository

import scala.collection.mutable

class ChatClient(val client: ActorRef) extends ChatManagement with SessionManagement with ChatServer {
  override val sessions: mutable.Map[String, ActorRef] = ChatRepository.sessions

  override def unhandled(message: Any): Unit = {
    println(s"Unhandled: $message")
  }

  override def aroundPostStop(): Unit = {
    println("Stoppping")
    super.aroundPostStop()
  }
}
