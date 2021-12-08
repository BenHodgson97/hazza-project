package controllers

import actions.AuthAction
import com.google.inject.{Inject, Singleton}
import forms.AbilityForm
import models.request.AuthenticatedRequest
import play.api.mvc._
import services.AbilityService
import views.html.AbilityListView

import scala.concurrent.ExecutionContext


@Singleton
class AbilityController @Inject()(
                                  val controllerComponents: ControllerComponents,
                                  abilityService: AbilityService,
                                  abilityListView: AbilityListView,
                                  authAction: AuthAction,
                                  abilityForm: AbilityForm
                                )(implicit executionContext: ExecutionContext) extends BaseController {

  def index: Action[AnyContent] = authAction.async {implicit request: AuthenticatedRequest[AnyContent] =>
    abilityService.getAbilityListItems.map {
      case (spellAndUpgrades, specials) =>
        Ok(abilityListView(spellAndUpgrades, specials))
    }
  }

  def onSubmit: Action[AnyContent] = authAction.async {
    implicit request =>
      abilityForm.form.bindFromRequest().fold (
        hasErrors => {
          abilityService.getAbilityListItems.map {
            case (spellAndUpgrades, specials) =>
              BadRequest(abilityListView(spellAndUpgrades, specials))
          }
        },
        abilityQuery => {
          abilityService.query(abilityQuery).map {
            case (spellAndUpgrades, specials) =>
              Ok(abilityListView(spellAndUpgrades, specials))
          }
        }
      )
  }
}

/** TODO
 * Sort abilities in main list by letter and sort upgrades by rank
 * for later, consider changes to make abilities into one list?
 */
