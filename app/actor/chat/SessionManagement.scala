package actor.chat

import akka.actor.{Actor, ActorRef}
import models.chat.{Login, Logout}

import scala.collection.mutable

trait SessionManagement { this: Actor =>

  def sessions: mutable.Map[String, ActorRef]
  val client: ActorRef

  protected def sessionManagement: Receive = {
    case Login(username) =>
      sessions += (username -> client)

    case Logout(username) =>
      sessions -= username
  }
}
