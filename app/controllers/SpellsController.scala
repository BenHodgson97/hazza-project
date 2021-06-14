package controllers

import com.google.inject.{Inject, Singleton}
import play.api.mvc._
import repositories.SpellRepository

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SpellsController @Inject()(val controllerComponents: ControllerComponents, spellRepository: SpellRepository)(implicit executionContext: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action.async { implicit request: Request[AnyContent] =>
    spellRepository.getAllSpells.map { spells =>
      Ok(views.html.index(spells))
    }
  }
}
