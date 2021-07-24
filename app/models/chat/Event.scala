package models.chat

import play.api.libs.json._
import play.api.mvc.WebSocket.MessageFlowTransformer

sealed trait Event
sealed trait OutgoingEvent extends Event
object Event {
  implicit val readsEvent: Reads[Event] = (json: JsValue) => {
    (json \ "EventType").as[String] match {
      case "Login" => json.validate[Login]
      case "Logout" => json.validate[Logout]
      case "ChatMessage" => json.validate[OutgoingMessage]
      case "IncomingMessage" => json.validate[IncomingMessage]
      case "Ping" => JsSuccess(Ping)
    }
  }

  implicit val writesEvent: Writes[OutgoingEvent] = {
    case chatMessage: ChatMessage => implicitly[Writes[ChatMessage]].writes(chatMessage)
    case Pong => implicitly[Writes[Pong.type]].writes(Pong)
  }

  implicit val messageFlowTransformer: MessageFlowTransformer[Event, OutgoingEvent] =
    MessageFlowTransformer.jsonMessageFlowTransformer[Event, OutgoingEvent]
}

case class Login(user: String) extends Event
object Login {
  implicit val readsLogin: Reads[Login] = Json.reads[Login]
}

case class Logout(user: String) extends Event
object Logout {
  implicit val readsLogout: Reads[Logout] = Json.reads[Logout]
}

sealed trait ChatMessage extends OutgoingEvent {
  val from: String
  val to: String
  val message: String
}

case class IncomingMessage(from: String, to: String, message: String) extends ChatMessage
case class OutgoingMessage(from: String, to: String, message: String) extends ChatMessage {
  def toIncomingMessage: IncomingMessage = IncomingMessage(from, to, message)
}

object OutgoingMessage {
  implicit val readsOutgoingMessage: Reads[OutgoingMessage] = Json.reads[OutgoingMessage]
  implicit val writesOutgoingMessage: Writes[OutgoingMessage] = Json.writes[OutgoingMessage].transform {
    json: JsObject => json + ("EventType", JsString("OutgoingMessage"))
  }
}

object IncomingMessage {
  implicit val readsIncomingMessage: Reads[IncomingMessage] = Json.reads[IncomingMessage]
  implicit val writesIncomingMessage: Writes[IncomingMessage] = Json.writes[IncomingMessage].transform {
    json: JsObject => json + ("EventType", JsString("OutgoingMessage"))
  }
}

object ChatMessage {
  implicit val writeChatMessage: Writes[ChatMessage] = {
    case incomingMessage: IncomingMessage => Json.toJson(incomingMessage)
    case outgoingMessage: OutgoingMessage => Json.toJson(outgoingMessage)
  }
}

case object Ping extends Event
case object Pong extends OutgoingEvent {
  implicit val writesPong: Writes[Pong.type] = (_: Pong.type) => Json.obj("EventType" -> "Pong")
}
