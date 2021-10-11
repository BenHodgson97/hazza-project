package repositories

import akka.actor.ActorRef
import com.google.inject.Singleton
import models.dice.Die
import models.dice.Die._

import scala.collection.mutable

@Singleton
class DiceRollerRepository {
  val clients: mutable.Buffer[ActorRef] = mutable.Buffer.empty
  val currentDiceState: mutable.Map[Die, Int] = mutable.Map(
    YellowProficiency -> 0,
    GreenAbility      -> 0,
    BlueBoost         -> 0,
    RedChallenge      -> 0,
    PurpleDifficulty  -> 0,
    BlackSetback      -> 0
  )
}
