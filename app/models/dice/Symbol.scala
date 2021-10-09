package models.dice

import play.api.libs.json.{JsString, Writes}

sealed trait Symbol

object Symbol {
  implicit val symbolWrites: Writes[Symbol] = (symbol: Symbol) => JsString(symbol.toString.toLowerCase)
  case object Success extends Symbol
  case object Advantage extends Symbol
  case object Triumph extends Symbol
  case object Failure extends Symbol
  case object Threat extends Symbol
  case object Despair extends Symbol
}
