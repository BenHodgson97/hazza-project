package models

import play.api.libs.json.Reads

sealed trait UpgradeStat

object UpgradeStat {
  case object Ferocity extends UpgradeStat
  case object Intellect extends UpgradeStat
  case object Cunning extends UpgradeStat
  case object Willpower extends UpgradeStat
  case object Finesse extends UpgradeStat
  case object Soak extends UpgradeStat
  case object Wounds extends UpgradeStat
  case object Strain extends UpgradeStat

  implicit val upgradeStatReads: Reads[UpgradeStat] = implicitly[Reads[String]].map {
    case "Ferocity" => Ferocity
    case "Intellect" => Intellect
    case "Cunning" => Cunning
    case "Willpower" => Willpower
    case "Finesse" => Finesse
    case "Soak" => Soak
    case "Wounds" => Wounds
    case "Strain" => Strain
  }
}
