package models.character

import play.api.libs.json.{Json, Reads}

case class CharacterSheet(info: Information, wandInfo: WandInformation, statistics: Statistics, thresholds: Thresholds) {

}

object CharacterSheet {
  implicit val characterSheetReads: Reads[CharacterSheet] = Json.reads[CharacterSheet]
}
