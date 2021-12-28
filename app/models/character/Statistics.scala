package models.character

import models.character.Characteristic._
import play.api.libs.json.{Json, Reads}

import scala.collection.immutable.ListMap

case class Statistics(ferocityStat: Int, intellectStat: Int, willpowerStat: Int, cunningStat: Int, finesseStat: Int) {
  def statsMap: ListMap[Characteristic, Int] = {
    ListMap(
      Ferocity -> ferocityStat,
      Intellect -> intellectStat,
      Willpower -> willpowerStat,
      Cunning -> cunningStat,
      Finesse -> finesseStat
    )
  }
}

object Statistics {
  implicit val statisticsReads: Reads[Statistics] = Json.reads[Statistics]
}
