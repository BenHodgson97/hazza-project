package actor.chat

import akka.actor.{Actor, ActorRef}
import models.chat.{Login, Logout}

import scala.collection.mutable

trait SessionManagement { this: Actor =>

  val sessions: mutable.Map[String, ActorRef]
  val client: ActorRef

  protected def sessionManagement: Receive = {
    case Login(username) =>
      println("LOGIN" + username)
      sessions += (username -> client)

    case Logout(username) =>
      sessions -= username
  }
}