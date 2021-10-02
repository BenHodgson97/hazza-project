package actor.dice

import akka.actor.{Actor, ActorRef}
import com.google.inject.Inject
import models.dice.{DiceUpdate, Die}
import repositories.DiceRollerRepository

import scala.collection.mutable

class DiceRollerActor(clients: mutable.Buffer[ActorRef], currentClient: ActorRef, currentDiceState: mutable.Map[Die, Int]) extends Actor {
  val now = System.currentTimeMillis()
  override def preStart(): Unit = {
    clients += currentClient
    super.preStart()
  }

  override def postStop(): Unit = {
    clients -= currentClient
    println(System.currentTimeMillis() - now)
    super.postStop()
  }

  override def receive: Receive = {
    case diceUpdate: DiceUpdate =>
      clients.filterNot(_ == currentClient).foreach(_ ! diceUpdate)
      val mapUpdateAmount: Int = {
        if (diceUpdate.amount + 1 == currentDiceState(diceUpdate.die)) {
          0
        } else {
          diceUpdate.amount + 1
        }
      }
      currentDiceState.update(diceUpdate.die, mapUpdateAmount)
      println(currentDiceState)
  }
}

class DiceRollerActorProvider @Inject()(diceRollerRepository: DiceRollerRepository) {
  def createActor(clientActor: ActorRef): Actor = new DiceRollerActor(diceRollerRepository.clients, clientActor, diceRollerRepository.currentDiceState)
}