package models.character

import play.api.libs.json.{Json, Reads}

import scala.collection.immutable.ListMap

case class Thresholds(soak: Threshold, wounds: Threshold, strain: Threshold) {
  def thresholdsMap: ListMap[String, Threshold] = {
    ListMap(
      "Soak" -> soak,
      "Wounds" -> wounds,
      "Strain" -> strain
    )
  }
}

object Thresholds {
  implicit val thresholdsReads: Reads[Thresholds] = Json.reads[Thresholds]
}
