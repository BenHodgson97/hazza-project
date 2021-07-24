package models.request

import play.api.mvc.{Request, WrappedRequest}

case class AuthenticatedRequest[A](request: Request[A], username: String) extends WrappedRequest(request)
