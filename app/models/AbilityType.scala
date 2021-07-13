package models

import play.api.libs.json.Reads

sealed trait AbilityType

object AbilityType {
  case object Spell extends AbilityType
  case object Upgrade extends AbilityType
  case object Stat extends AbilityType
  case object Special extends AbilityType

  implicit val abilityTypeReads: Reads[AbilityType] = implicitly[Reads[String]].map {
    case "Spell" => Spell
    case "Upgrade" => Upgrade
    case "Stat"=> Stat
    case "Special" => Special
  }
}
