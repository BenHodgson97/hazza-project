package controllers

import actions.{AuthAction, GetUserDataAction}
import com.google.inject.Inject
import models.request.AuthenticatedUserRequest
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import views.html.CharacterSheetView

class CharacterSheetController @Inject()(val controllerComponents: ControllerComponents,
                                         getUserDataAction: GetUserDataAction,
                                         authAction: AuthAction,
                                         characterSheetView: CharacterSheetView) extends BaseController {
  def onPageLoad: Action[AnyContent] = (authAction andThen getUserDataAction) {implicit request: AuthenticatedUserRequest[AnyContent] =>
    Ok(characterSheetView())
  }
}
