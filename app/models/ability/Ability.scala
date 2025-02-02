package models.ability

import play.api.libs.json.{JsValue, Json, Reads}

import scala.math.Integral.Implicits.infixIntegralOps

sealed trait Ability {
  val treePosition: Int
  private val (quotient, remainder) = (treePosition - 1) /% 4
  val gridColumn: Int = remainder + 1
  val gridRow: Int = quotient + 1
}

object Ability {

  implicit val abilityReads: Reads[Ability] = (json: JsValue) => {
    (json \ "abilityType").as[String] match {
      case "Spell" => json.validate[Spell]
      case "Upgrade" => json.validate[Upgrade]
      case "Stat" => json.validate[Stat]
      case "Special" => json.validate[Special]
    }
  }
}

case class Spell(
                  id: String,
                  name: String,
                  rank: Int,
                  opposing: Boolean,
                  spellType: Seq[SpellType],
                  skill: Seq[Skill],
                  cost: Int,
                  description: String,
                  group: Group,
                  treePosition: Int
                ) extends Ability

object Spell {
  implicit val spellsReads: Reads[Spell] = Json.reads[Spell]
}

case class Upgrade(
                    id: String,
                    name: String,
                    spellId: Seq[Int],
                    rank: Int,
                    cost: Int,
                    description: String,
                    group: Group,
                    treePosition: Int
                  ) extends Ability {
  val spellIdString: Seq[String] = spellId.map(i => s"spell$i")
}
object Upgrade {
  implicit val upgradeReads: Reads[Upgrade] = Json.reads[Upgrade]
}

case class Stat(
                 id: String,
                 name: String,
                 rank: Int,
                 cost: Int,
                 upgradeAmount: Int,
                 upgradeStat: UpgradeStat,
                 group: Group,
                 treePosition: Int,
               ) extends Ability {
  def description: String = s"+$upgradeAmount to $upgradeStat"
}

object Stat {
  implicit val statReads: Reads[Stat] = Json.reads[Stat]
}

case class Special(
                    id: String,
                    name: String,
                    rank: Int,
                    cost: Int,
                    activePassive: ActivePassive,
                    description: String,
                    group: Group,
                    treePosition: Int
                  ) extends Ability

object Special {
  implicit val specialReads: Reads[Special] = Json.reads[Special]
}
