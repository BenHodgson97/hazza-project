package models.dice

import models.dice.Die.{BlackSetback, BlueBoost, GreenAbility, PurpleDifficulty, RedChallenge, YellowProficiency}
import play.api.libs.json.{JsString, Json, Reads, Writes}
import play.api.mvc.WebSocket.MessageFlowTransformer

case class DiceUpdate(die: Die, amount: Int)

object DiceUpdate {
  implicit val dieReads: Reads[Die] = implicitly[Reads[String]].map {
    case "yellow" => YellowProficiency
    case "green" => GreenAbility
    case "blue" => BlueBoost
    case "red" => RedChallenge
    case "purple" => PurpleDifficulty
    case "black" => BlackSetback
  }

  implicit val dieWrites: Writes[Die] = {
    case YellowProficiency => JsString("yellow")
    case GreenAbility => JsString("green")
    case BlueBoost => JsString("blue")
    case RedChallenge => JsString("red")
    case PurpleDifficulty => JsString("purple")
    case BlackSetback => JsString("black")
  }

  implicit val diceUpdateReads: Reads[DiceUpdate] = Json.reads[DiceUpdate]
  implicit val diceUpdateWrites: Writes[DiceUpdate] = Json.writes[DiceUpdate]
  implicit val messageFlowTransformer: MessageFlowTransformer[DiceUpdate, DiceUpdate] =
    MessageFlowTransformer.jsonMessageFlowTransformer[DiceUpdate, DiceUpdate]
}
