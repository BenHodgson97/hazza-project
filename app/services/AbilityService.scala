package services

import com.google.inject.Inject
import models.{Ability, Special, Spell, SpellAndUpgrades, Upgrade}
import repositories.AbilityRepository

import scala.concurrent.{ExecutionContext, Future}

class AbilityService @Inject()(abilityRepository: AbilityRepository)(implicit executionContext: ExecutionContext){
  def attachUpgrades(abilities: Seq[Ability]): Seq[SpellAndUpgrades] = {
    val spellList: Seq[Spell] = abilities.collect {
      case spell: Spell => spell
    }

    val upgradeList: Seq[Upgrade] = abilities.collect {
      case upgrade: Upgrade => upgrade
    }

    spellList.map { spell =>
      val upgrades = upgradeList.filter(upgrade => upgrade.spellId.contains(spell.id))

      SpellAndUpgrades(spell, upgrades)
    }
  }

  def collectSpecials(abilities: Seq[Ability]): Seq[Special] = {
    abilities.collect {
      case special: Special => special
    }
  }

  def getAbilityListItems: Future[(Seq[SpellAndUpgrades], Seq[Special])] = abilityRepository.getAbilityListItems.map {
    abilities =>
      (attachUpgrades(abilities), collectSpecials(abilities))
  }
}
