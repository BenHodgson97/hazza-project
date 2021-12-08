package models.character

sealed trait Characteristic

object Characteristic {
  case object Ferocity extends Characteristic
  case object Intellect extends Characteristic
  case object Cunning extends Characteristic
  case object Willpower extends Characteristic
  case object Finesse extends Characteristic
}
