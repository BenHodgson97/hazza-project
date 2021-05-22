package repositories

import com.google.inject.Inject
import config.AppConfig
import models.Spells


class SpellRepository @Inject()(appConfig: AppConfig){

  val spell: Spells = Spells(1, "spellName", 1, "spellType", "skill", "abilityType", 1, "description", 1, 1)

  def getAllSpells: List[Spells] = ???

}
