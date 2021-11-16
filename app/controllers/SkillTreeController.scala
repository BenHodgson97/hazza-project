package controllers

import actions.{AuthAction, GetUserDataAction}
import com.google.inject.{Inject, Singleton}
import models.ability.Group
import models.ability.Group.Light
import models.request.AuthenticatedUserRequest
import play.api.mvc._
import repositories.{AbilityRepository, UserRepository}
import views.html.SkillTreeView

import scala.concurrent.ExecutionContext

@Singleton
class SkillTreeController @Inject()(
                                   val controllerComponents: ControllerComponents,
                                   skillTreeView: SkillTreeView,
                                   abilityRepository: AbilityRepository,
                                   userRepository: UserRepository,
                                   authAction: AuthAction,
                                   getUserDataAction: GetUserDataAction
                                   )(implicit executionContext: ExecutionContext) extends BaseController {

  def onPageLoad(group: Group): Action[AnyContent] = (authAction andThen getUserDataAction).async { implicit request: AuthenticatedUserRequest[AnyContent] =>

    abilityRepository.getAbilityByGroup(group).map {
      abilities => Ok(skillTreeView(abilities, group.toString))
    }
  }

  def loadRoot: Action[AnyContent] = (authAction andThen getUserDataAction) { _ =>
    Redirect(controllers.routes.SkillTreeController.onPageLoad(Light))
  }

  def addAbility(username: String, abilityId: String): Action[AnyContent] = Action.async { _ =>
    userRepository.addAbilityToUser(username, abilityId).map {
      _ => NoContent
    }
  }

  def removeAbility(username: String, abilityId: String): Action[AnyContent] = Action.async { _ =>
    userRepository.removeAbilityFromUser(username, abilityId).map {
      _ => NoContent
    }
  }
}
