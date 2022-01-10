package models.character

import models.character.RankAndXp.{MagicRank, magicXpTable}
import play.api.libs.json.{Json, Reads}

import scala.collection.immutable.TreeMap

case class RankAndXp(magicXp: Int, totalXp: Int, currentXp: Int) {
  val magicRank: MagicRank = magicXpTable.maxBefore(magicXp).getOrElse((0, 0))._2
}

object RankAndXp {
  type MagicRank = Int
  type Xp = Int
  val magicXpTable: TreeMap[Xp, MagicRank] = TreeMap(
    -1 -> 1,
    9 -> 2,
    29 -> 3,
    69 -> 4,
    139 -> 5,
    229 -> 6,
    359 -> 7,
    539 -> 8,
    749 -> 9,
    1019 -> 10,
    1349 -> 11,
    1739 -> 12,
    2189 -> 13,
    2719 -> 14,
    3319 -> 15,
    3999 -> 16,
    4769 -> 17,
    5629 -> 18,
    6589 -> 19
  )

  implicit val rankAndXpReads: Reads[RankAndXp] = Json.reads[RankAndXp]
}
