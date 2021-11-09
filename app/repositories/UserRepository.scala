package repositories

import com.google.inject.Inject
import models.User
import play.api.libs.json.{JsObject, Json, OWrites}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}

import scala.concurrent.{ExecutionContext, Future}

class UserRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit executionContext: ExecutionContext){
  implicit final val jsObjectWrites: OWrites[JsObject] =
    OWrites[JsObject](identity)

  def collection: Future[BSONCollection] = reactiveMongoApi.database.map {
    _.collection[BSONCollection]("Users")
  }

  def getUserRecord(username: String): Future[Option[User]] = {
    collection.flatMap {
      collection =>
        val query = Json.obj("username" -> username)
        collection.find(query).one[User]
    }
  }
}
