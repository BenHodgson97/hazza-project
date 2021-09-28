package actor.dice

import akka.actor.{Actor, ActorRef}
import models.dice.DiceUpdate

import scala.collection.mutable

class DiceRollerActor(clients: mutable.Buffer[ActorRef], currentClient: ActorRef) extends Actor {
  override def receive: Receive = {
    case diceUpdate: DiceUpdate =>
      clients.filterNot(_ == currentClient).foreach(_ ! diceUpdate)
  }
}

