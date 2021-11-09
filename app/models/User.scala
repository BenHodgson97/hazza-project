package models

import play.api.libs.json.{Json, Reads}

case class User(username: String, skillsOwned: Seq[String])

object User {
  implicit val userReads: Reads[User] = Json.reads[User]
}
