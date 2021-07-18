package repositories

import akka.actor.ActorRef

import scala.collection.mutable

object ChatRepository {
  val sessions: mutable.Map[String, ActorRef] = mutable.Map.empty
}
