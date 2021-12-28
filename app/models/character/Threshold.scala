package models.character

import play.api.libs.json.{Json, Reads}

case class Threshold(threshold: Int, current: Int)

object Threshold {
  implicit val thresholdReads: Reads[Threshold] = Json.reads[Threshold]
}