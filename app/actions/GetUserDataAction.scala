package actions

import com.google.inject.{ImplementedBy, Inject}
import models.request.{AuthenticatedRequest, AuthenticatedUserRequest}
import play.api.mvc.Results.ImATeapot
import play.api.mvc.{ActionRefiner, Result}
import repositories.UserRepository

import scala.concurrent.{ExecutionContext, Future}

class GetUserDataActionImpl @Inject()(userRepository: UserRepository)(implicit val executionContext: ExecutionContext) extends GetUserDataAction {
  override protected def refine[A](request: AuthenticatedRequest[A]): Future[Either[Result, AuthenticatedUserRequest[A]]] = {
    userRepository.getUserRecord(request.username).map {
      optUser => optUser.fold[Either[Result, AuthenticatedUserRequest[A]]](
        Left(ImATeapot("Get fucked and try again"))
      )(
        user => Right(AuthenticatedUserRequest(request, user))
      )
    }
  }
}

@ImplementedBy(classOf[GetUserDataActionImpl])
trait GetUserDataAction extends ActionRefiner[AuthenticatedRequest, AuthenticatedUserRequest]
