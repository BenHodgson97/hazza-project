package actor.chat

import akka.actor.{Actor, ActorRef}
import models.chat.{IncomingMessage, OutgoingMessage}

import scala.collection.mutable

trait ChatManagement { this: Actor =>
  val sessions: mutable.Map[String, ActorRef]
  val client: ActorRef

  protected def chatManagement: Receive = {
    case msg @ OutgoingMessage(_, to, _) => getSession(to).foreach(_ ! msg.toIncomingMessage)
    case msg: IncomingMessage => client ! msg
  }
  //TODO error handling n shit
  private def getSession(to: String) : Option[ActorRef] =
    sessions.get(to)
}
