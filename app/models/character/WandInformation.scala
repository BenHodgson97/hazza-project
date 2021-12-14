package models.character

import play.api.libs.json.{Json, Reads}

case class WandInformation(core: String, wood: String, currentWand: String, health: Int)

object WandInformation {
  implicit val wandInformationReads: Reads[WandInformation] = Json.reads[WandInformation]
}

//TODO look at enumerating core and wood.
//TODO Consider tooltips for wand components
