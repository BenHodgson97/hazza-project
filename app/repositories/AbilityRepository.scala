package repositories

import com.google.inject.{Inject, Singleton}
import models.Ability
import play.api.libs.json.{JsObject, Json, OWrites}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.play.json.compat._
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AbilityRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit executionContext: ExecutionContext) {

  implicit final val jsObjectWrites: OWrites[JsObject] =
    OWrites[JsObject](identity)

  private def collection: Future[BSONCollection] = reactiveMongoApi.database.map {
    db => db.collection[BSONCollection]("Abilities")
  }

  def getAbilityListItems: Future[Seq[Ability]] = collection.flatMap {
    collection =>
      val query = Json.obj(
        "$or" -> Json.arr(
          Json.obj("abilityType" -> "Spell"),
          Json.obj("abilityType" -> "Upgrade"),
          Json.obj("abilityType" -> "Special")
        )
      )

      collection.find(query).cursor[Ability]().collect[Seq]()
  }


}
