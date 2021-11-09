package models.request

import models.User
import play.api.mvc.{Request, WrappedRequest}

abstract class RequestWithName[A](request: Request[A]) extends WrappedRequest(request) {
  val username: String
}

case class AuthenticatedRequest[A](request: Request[A], username: String) extends RequestWithName(request)

case class AuthenticatedUserRequest[A](request: Request[A], userData: User) extends RequestWithName(request) {
  val username: String = userData.username
}
