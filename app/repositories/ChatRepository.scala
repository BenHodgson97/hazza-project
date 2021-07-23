package repositories

import akka.actor.ActorRef
import com.google.inject.Singleton

import scala.collection.mutable

@Singleton
class ChatRepository {
  val sessions: mutable.Map[String, ActorRef] = mutable.Map.empty
}
