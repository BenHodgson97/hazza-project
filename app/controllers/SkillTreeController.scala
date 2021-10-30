package controllers

import actions.AuthAction
import com.google.inject.{Inject, Singleton}
import models.ability.Group
import models.ability.Group.Light
import models.request.AuthenticatedRequest
import play.api.mvc._
import repositories.AbilityRepository
import views.html.SkillTreeView

import scala.concurrent.ExecutionContext

@Singleton
class SkillTreeController @Inject()(
                                   val controllerComponents: ControllerComponents,
                                   skillTreeView: SkillTreeView,
                                   abilityRepository: AbilityRepository,
                                   authAction: AuthAction
                                   )(implicit executionContext: ExecutionContext) extends BaseController {

  def onPageLoad(group: Group): Action[AnyContent] = authAction.async { implicit request: AuthenticatedRequest[AnyContent] =>

    abilityRepository.getAbilityByGroup(group).map {
      abilities => Ok(skillTreeView(abilities, group.toString))
    }
  }

  def loadRoot: Action[AnyContent] = authAction { _ =>
    Redirect(controllers.routes.SkillTreeController.onPageLoad(Light))
  }
}
