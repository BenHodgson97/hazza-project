package actor.dice

import akka.actor.{Actor, ActorRef}
import com.google.inject.Inject
import models.dice.{DiceUpdate, Die, Roll, RollResult}
import play.api.libs.json.Json
import repositories.DiceRollerRepository
import services.DiceCancellingService

import scala.collection.mutable

class DiceRollerActor(clients: mutable.Buffer[ActorRef],
                      currentClient: ActorRef,
                      currentDiceState: mutable.Map[Die, Int],
                      diceCancellingService: DiceCancellingService) extends Actor {
  override def preStart(): Unit = {
    clients += currentClient
    super.preStart()
  }

  override def postStop(): Unit = {
    clients -= currentClient
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

    case Roll =>
      val diceToRoll = currentDiceState.foldLeft(Seq.empty[Die]) {
        case (acc, (die, amount)) => acc ++ Seq.fill(amount)(die)
      }

      val rolledDice =
        diceToRoll.map(die => (die, die.roll.symbols)).groupMap(tuple => tuple._1)(tuple => tuple._2)

      val symbolList = rolledDice.values.flatten.flatten.toSeq
      val cancelledSymbols = diceCancellingService.cancelSymbols(symbolList)
      val rollResult = RollResult(cancelledSymbols, rolledDice)
      clients.foreach(_ ! rollResult)
  }
}

class DiceRollerActorProvider @Inject()(diceRollerRepository: DiceRollerRepository, diceCancellingService: DiceCancellingService) {
  def createActor(clientActor: ActorRef): Actor = new DiceRollerActor(diceRollerRepository.clients, clientActor, diceRollerRepository.currentDiceState, diceCancellingService)
}