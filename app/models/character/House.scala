package models.character

import models.character.Characteristic._
import play.api.libs.json.{JsValue, Json, Reads}

sealed trait House {
  val primary: Characteristic
  val secondary: Characteristic

  val displayName: String = this.getClass.getSimpleName

  lazy val displayString = s"$displayName($primary & $secondary)"
}

object House {
  case class Gryffindor(secondary: Characteristic) extends House {
    override val primary: Characteristic = Willpower
  }

  case class Hufflepuff(secondary: Characteristic) extends House {
    override val primary: Characteristic = Finesse
  }

  case class Ravenclaw(secondary: Characteristic) extends House {
    override val primary: Characteristic = Intellect
  }

  case class FerocitySlytherin(secondary: Characteristic) extends House {
    override val primary: Characteristic = Ferocity
    override val displayName: String = "Slytherin"
  }

  case class CunningSlytherin(secondary: Characteristic) extends House {
    override val primary: Characteristic = Cunning
    override val displayName: String = "Slytherin"
  }

  implicit val gryffindorReads: Reads[Gryffindor] = Json.reads[Gryffindor]
  implicit val hufflepuffReads: Reads[Hufflepuff] = Json.reads[Hufflepuff]
  implicit val ravenclawReads: Reads[Ravenclaw] = Json.reads[Ravenclaw]
  implicit val ferocitySlytherinReads: Reads[FerocitySlytherin] = Json.reads[FerocitySlytherin]
  implicit val cunningSlytherinReads: Reads[CunningSlytherin] = Json.reads[CunningSlytherin]

  implicit val houseReads: Reads[House] = (json: JsValue) => {
    (json \ "name").as[String] match {
      case "Gryffindor" => json.validate[Gryffindor]
      case "Hufflepuff" => json.validate[Hufflepuff]
      case "Ravenclaw" => json.validate[Ravenclaw]
      case "FerocitySlytherin" => json.validate[FerocitySlytherin]
      case "CunningSlytherin" => json.validate[CunningSlytherin]
    }
  }
}
