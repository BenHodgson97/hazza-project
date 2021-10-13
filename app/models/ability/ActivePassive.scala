package models.ability

import play.api.libs.json.Reads

sealed trait ActivePassive

object ActivePassive {
  case object Active extends ActivePassive
  case object Passive extends ActivePassive


  implicit val activePassiveReads: Reads[ActivePassive] = implicitly[Reads[String]].map {
    case "Active" => Active
    case "Passive" => Passive
  }
}

