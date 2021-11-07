package models.request

import models.User
import play.api.mvc.{Request, WrappedRequest}

case class AuthenticatedRequest[A](request: Request[A], username: String) extends WrappedRequest(request)

case class AuthenticatedUserRequest[A](request: Request[A], userData: User) extends WrappedRequest(request)