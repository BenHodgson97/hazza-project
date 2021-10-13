package models.ability

import play.api.libs.json.Reads

sealed trait Group

object Group {
  case object Light extends Group
  case object Teleportation extends Group
  case object Telekinesis extends Group
  case object Water extends Group
  case object Transfiguration extends Group
  case object Fire extends Group
  case object Curses extends Group
  case object Support extends Group
  case object Reveals extends Group
  case object Brainwashing extends Group
  case object Defence extends Group
  case object Environmental extends Group
  case object Mobility extends Group
  case object Counters extends Group
  case object Offense extends Group

  implicit val groupReads: Reads[Group] = implicitly[Reads[String]].map {
    case "Light" => Light
    case "Teleportation" => Teleportation
    case "Telekinesis" => Telekinesis
    case "Water" => Water
    case "Transfiguration" => Transfiguration
    case "Fire" => Fire
    case "Curses" => Curses
    case "Support" => Support
    case "Reveals" => Reveals
    case "Brainwashing" => Brainwashing
    case "Defence" => Defence
    case "Environmental" => Environmental
    case "Mobility" => Mobility
    case "Counters" => Counters
    case "Offense" => Offense
  }
}
