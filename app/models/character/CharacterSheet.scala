package models.character

import play.api.libs.json.{Json, Reads}

case class CharacterSheet(info: Information, wandInfo: WandInformation) {

}

object CharacterSheet {
  implicit val characterSheetReads: Reads[CharacterSheet] = Json.reads[CharacterSheet]
}
