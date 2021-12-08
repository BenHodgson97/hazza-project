package repositories

import com.google.inject.{Inject, Singleton}
import models.AbilityQuery
import models.ability.{Ability, Group}
import play.api.libs.json.{JsObject, JsString, Json, OWrites}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}
import Json.toJsFieldJsValueWrapper

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AbilityRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit executionContext: ExecutionContext) {

  implicit final val jsObjectWrites: OWrites[JsObject] =
    OWrites[JsObject](identity)

  def collection: Future[BSONCollection] = reactiveMongoApi.database.map {
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

  def getAbilityByGroup(group: Group): Future[Seq[Ability]] = collection.flatMap {
    collection =>
      val query = Json.obj("group" -> group.toString)
      collection.find(query).cursor[Ability]().collect[Seq]()
  }

  def query(abilityQuery: AbilityQuery): Future[Seq[Ability]] = collection.flatMap {
    collection =>
      val searchJson: Option[JsObject] = abilityQuery.searchToRegex.map(
        search => Json.obj("name" -> Json.obj("$regex" -> search, "$options" -> "i"))
      )

      val upgradeJson: Option[JsObject] = Some(Json.obj("abilityType" -> "Upgrade"))

      val groupJson: Option[JsObject] = abilityQuery.groupFilter.map(
        groups => Json.obj("group" -> Json.obj("$in" -> Json.arr(groups.map(group => toJsFieldJsValueWrapper(JsString(group.toString))):_*)))
      )

      val allJson: Seq[JsObject] = Seq(searchJson, upgradeJson, groupJson).flatten

      val query = Json.obj(
        "$or" -> Json.arr(allJson.map(json => toJsFieldJsValueWrapper(json)): _*)
      )

      collection.find(query).cursor[Ability]().collect[Seq]()
  }
}
