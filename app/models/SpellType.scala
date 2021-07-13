package models

import play.api.libs.json.Reads

sealed trait SpellType

object SpellType {
  case object Charm extends SpellType
  case object CounterCharm extends SpellType
  case object Jinx extends SpellType
  case object CounterJinx extends SpellType
  case object Hex extends SpellType
  case object Curse extends SpellType
  case object CounterSpell extends SpellType
  case object Conjuration extends SpellType
  case object HealingSpell extends SpellType
  case object Transfiguration extends SpellType
  case object Transportation extends SpellType

  implicit val spellTypeReads: Reads[SpellType] = implicitly[Reads[String]].map {
    case "Charm" => Charm
    case "CounterCharm" => CounterCharm
    case "Jinx" => Jinx
    case "CounterJinx" => CounterJinx
    case "Hex" => Hex
    case "Curse" => Curse
    case "CounterSpell" => CounterSpell
    case "Conjuration" => Conjuration
    case "HealingSpell" => HealingSpell
    case "Transfiguration" => Transfiguration
    case "Transportation" => Transportation
  }
}
