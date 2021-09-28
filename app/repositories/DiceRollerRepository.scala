package repositories

import akka.actor.ActorRef
import com.google.inject.Singleton

import scala.collection.mutable

@Singleton
class DiceRollerRepository {
  val sessions: mutable.Buffer[ActorRef] = mutable.Buffer.empty
}
