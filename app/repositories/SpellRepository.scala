package repositories

import com.google.inject.{Inject, Singleton}
import models.Spells
import play.modules.reactivemongo.ReactiveMongoApi

@Singleton
class SpellRepository @Inject()(reactiveMongoApi: ReactiveMongoApi){

  val spell: Spells = Spells(1, "spellName", 1, "spellType", "skill", "abilityType", 1, "description", 1, 1)

  def getAllSpells: List[Spells] = ???

}
