package controllers

import com.google.inject.{Inject, Singleton}
import models.Spell
import models.request.AuthenticatedRequest
import play.api.mvc._
import repositories.AbilityRepository
import views.html.AbilityListView

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AbilityController @Inject()(
                                  val controllerComponents: ControllerComponents,
                                  abilityRepository: AbilityRepository,
                                  abilityListView: AbilityListView
                                )(implicit executionContext: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index(username: Option[String]): Action[AnyContent] = Action.async { request: Request[AnyContent] =>
    implicit val authenticatedRequest: AuthenticatedRequest[AnyContent] = new AuthenticatedRequest[AnyContent](request, username.getOrElse("default"))
    abilityRepository.getAbilityListItems.map {
      abilities =>
        Ok(abilityListView(abilities))
    }
  }
}
