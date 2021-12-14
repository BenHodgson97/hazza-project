package models.character

import play.api.libs.json.{Json, Reads}

case class Information(name: String,
                       features: String,
                       gender: String,
                       height: String,
                       build: String,
                       player: String,
                       signatureSpell: String,
                       patronus: String,
                       house: House)

object Information {
  implicit val informationReads: Reads[Information] = Json.reads[Information]
}
