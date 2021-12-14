package models.character

import play.api.libs.json.Reads

sealed trait Characteristic

object Characteristic {
  case object Ferocity extends Characteristic
  case object Intellect extends Characteristic
  case object Cunning extends Characteristic
  case object Willpower extends Characteristic
  case object Finesse extends Characteristic

  implicit val characteristicReads: Reads[Characteristic] = implicitly[Reads[String]].map {
    case "Ferocity" => Ferocity
    case "Intellect" => Intellect
    case "Cunning" => Cunning
    case "Willpower" => Willpower
    case "Finesse" => Finesse
  }
}
