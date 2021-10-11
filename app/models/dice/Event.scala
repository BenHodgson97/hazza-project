package models.dice

import models.dice.Die._
import play.api.libs.json._
import play.api.mvc.WebSocket.MessageFlowTransformer

sealed trait Event

object Event {
  implicit val eventReads: Reads[Event] = (json: JsValue) => {
    (json \ "EventType").as[String] match {
      case "Roll" => JsSuccess(Roll)
      case "Ping" => JsSuccess(Ping)
      case "DiceUpdate" => json.validate[DiceUpdate]
    }
  }

  implicit val messageFlowTransformer: MessageFlowTransformer[Event, OutgoingEvent] =
    MessageFlowTransformer.jsonMessageFlowTransformer[Event, OutgoingEvent]
}

sealed trait OutgoingEvent

object OutgoingEvent {
  implicit val outgoingEventWrites: Writes[OutgoingEvent] = {
    case diceUpdate: DiceUpdate => Json.toJson(diceUpdate)
    case rollResult: RollResult => Json.toJson(rollResult)
  }
}

case class RollResult(symbols: Seq[Symbol], rolledDice: Map[Die, Seq[Seq[Symbol]]]) extends OutgoingEvent

object RollResult {
  implicit val mapWrites: Writes[Map[Die, Seq[Seq[Symbol]]]] = {
    map =>
      map.foldLeft(JsObject.empty) {
        case (acc, (die, results)) =>
          val resultsArray: Seq[JsValue] = results.map(result => JsArray(result.map(symbol => Json.toJson(symbol))))
          acc + (die.colour, JsArray(resultsArray))
      }
  }
  implicit val rollResultWrites: Writes[RollResult] = Json.writes[RollResult].transform {
    json: JsObject => json + ("EventType", JsString("RollResult"))
  }
}

case object Roll extends Event

case object Ping extends Event

case class DiceUpdate(die: Die, amount: Int) extends Event with OutgoingEvent

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
  implicit val diceUpdateWrites: Writes[DiceUpdate] = Json.writes[DiceUpdate].transform {
    json: JsObject => json + ("EventType", JsString("DiceUpdate"))
  }
}
