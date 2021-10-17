package controllers

import actions.AuthAction
import com.google.inject.{Inject, Singleton}
import models.request.AuthenticatedRequest
import play.api.mvc._
import views.html.SkillTreeView

import scala.concurrent.ExecutionContext

@Singleton
class SkillTreeController @Inject()(
                                   val controllerComponents: ControllerComponents,
                                   skillTreeView: SkillTreeView,
                                   authAction: AuthAction
                                   )(implicit executionContext: ExecutionContext) extends BaseController {

  def onPageLoad: Action[AnyContent] = authAction {implicit request: AuthenticatedRequest[AnyContent] =>
    Ok(skillTreeView())
  }
}
