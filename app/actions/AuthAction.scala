package actions

import com.google.inject.{ImplementedBy, Inject}
import models.request.AuthenticatedRequest
import play.api.mvc.Results.Redirect
import play.api.mvc.{ActionBuilder, ActionRefiner, AnyContent, BodyParser, BodyParsers, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

class AuthActionImplementation @Inject()(val executionContext: ExecutionContext,
                                         val parser: BodyParsers.Default) extends AuthAction {
  override protected def refine[A](request: Request[A]): Future[Either[Result, AuthenticatedRequest[A]]] = {
    Future.successful(
      request.session.get("username").fold[Either[Result, AuthenticatedRequest[A]]](
        Left(Redirect(controllers.routes.AuthController.onPageLoad))
      )(username => Right(AuthenticatedRequest(request, username)))
    )
  }
}
@ImplementedBy(classOf[AuthActionImplementation])
trait AuthAction extends ActionRefiner[Request, AuthenticatedRequest] with ActionBuilder[AuthenticatedRequest, AnyContent]
