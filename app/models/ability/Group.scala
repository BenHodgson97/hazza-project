package models.ability

import play.api.libs.json.{Reads, __}
import play.api.mvc.PathBindable

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

  def allGroups: Seq[Group] = Seq(
    Light,
    Teleportation,
    Telekinesis,
    Water,
    Transfiguration,
    Fire,
    Curses,
    Support,
    Reveals,
    Brainwashing,
    Defence,
    Environmental,
    Mobility,
    Counters,
    Offense
  )

  def fromString(string: String): Group = string.toLowerCase() match {
    case "light" => Light
    case "teleportation" => Teleportation
    case "telekinesis" => Telekinesis
    case "water" => Water
    case "transfiguration" => Transfiguration
    case "fire" => Fire
    case "curses" => Curses
    case "support" => Support
    case "reveals" => Reveals
    case "brainwashing" => Brainwashing
    case "defence" => Defence
    case "environmental" => Environmental
    case "mobility" => Mobility
    case "counters" => Counters
    case "offense" => Offense
  }

  implicit val pathBinder: PathBindable[Group] = new PathBindable[Group] {
    override def bind(key: String, value: String): Either[String, Group] = Right(fromString(value))

    override def unbind(key: String, value: Group): String = value.toString.toLowerCase
  }

  implicit val groupReads: Reads[Group] = implicitly[Reads[String]].map(fromString)
}
