package models

import play.api.libs.json.{Json, Reads}

case class Spells(
                   id: Int,
                   name: String,
                   rank: Int,
                   opposing: Boolean,
                   spellType: Seq[String], //TODO Enumerated
                   skill: Seq[String], //TODO Enumerated
                   abilityType: String, //TODO Enumerated
                   cost: Int,
                   description: String,
                   group: Int,
                   treePosition: Int
                 )



object Spells {
  implicit val spellsReads: Reads[Spells] = Json.reads[Spells]
}