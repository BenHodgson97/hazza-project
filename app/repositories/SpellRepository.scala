package repositories

import com.google.inject.{Inject, Singleton}
import models.Spells
import play.api.libs.json.JsObject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}
import reactivemongo.play.json.compat._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SpellRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit executionContext: ExecutionContext) {

  private def collection: Future[BSONCollection] = reactiveMongoApi.database.map {
    db => db.collection[BSONCollection]("Spells")
  }

  def getAllSpells: Future[Seq[Spells]] = collection.flatMap {
    collection => collection.find(JsObject.empty).cursor[Spells]().collect[Seq]()
  }


}
