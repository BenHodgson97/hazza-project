package repositories

import com.google.inject.Inject
import models.character.CharacterSheet
import play.api.libs.json.{JsObject, Json, OWrites}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}

import scala.concurrent.{ExecutionContext, Future}

class CharacterSheetRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit executionContext: ExecutionContext) {
  implicit final val jsObjectWrites: OWrites[JsObject] =
    OWrites[JsObject](identity)

  def collection: Future[BSONCollection] = reactiveMongoApi.database.map(_.collection[BSONCollection]("Character-Sheets"))

  def getSheetByUser(username: String): Future[Option[CharacterSheet]] = collection.flatMap {
    collection =>
      val query = Json.obj("username" -> username)
      collection.find(query).one[CharacterSheet]
  }
}
