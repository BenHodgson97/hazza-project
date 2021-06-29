package models

import play.api.libs.json.Reads

sealed trait Skill

object Skill {
  case object Charm extends Skill
  case object Control extends Skill
  case object Power extends Skill
  case object Transfiguration extends Skill
  case object Flying extends Skill
  case object Precision extends Skill
  case object Stealth extends Skill
  case object Resilience extends Skill
  case object Medicine extends Skill
  case object Skullduggery extends Skill
  case object Perception extends Skill
  case object Deception extends Skill
  case object Coercion extends Skill

  implicit val skillReads: Reads[Skill] = implicitly[Reads[String]].map {
    case "Charm" => Charm
    case "Control" => Control
    case "Power" => Power
    case "Transfiguration" => Transfiguration
    case "Flying" => Flying
    case "Precision" => Precision
    case "Stealth" => Stealth
    case "Resilience" => Resilience
    case "Medicine" => Medicine
    case "Skullduggery" => Skullduggery
    case "Perception" => Perception
    case "Deception" => Deception
    case "Coercion" => Coercion
  }
}
