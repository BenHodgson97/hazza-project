package models.dice

import models.dice.Die._
import play.api.libs.json._
import play.api.mvc.WebSocket.MessageFlowTransformer

sealed trait Event

object Event {
  implicit val eventReads: Reads[Event] = (json: JsValue) => {
    (json \ "EventType").as[String] match {
      case "DiceUpdate" => json.validate[DiceUpdate]
      case "Ping" => JsSuccess(Ping)
    }
  }

  implicit val messageFlowTransformer: MessageFlowTransformer[Event, DiceUpdate] =
    MessageFlowTransformer.jsonMessageFlowTransformer[Event, DiceUpdate]
}

case object Ping extends Event

case class DiceUpdate(die: Die, amount: Int) extends Event

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
}
