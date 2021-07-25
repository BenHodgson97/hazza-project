package actor.chat

import akka.actor.{Actor, ActorRef}
import models.chat.{Ping, Pong}

trait KeepAlive { this: Actor =>

  val client: ActorRef

  protected def keepAlive: Receive = {
    case Ping =>
      client ! Pong
  }

}
