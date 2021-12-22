package controllers

import actions.{AuthAction, GetUserDataAction}
import com.google.inject.Inject
import models.request.AuthenticatedUserRequest
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import repositories.CharacterSheetRepository
import views.html.CharacterSheetView

import scala.concurrent.ExecutionContext

class CharacterSheetController @Inject()(
                                          val controllerComponents: ControllerComponents,
                                          getUserDataAction: GetUserDataAction,
                                          authAction: AuthAction,
                                          characterSheetRepository: CharacterSheetRepository,
                                          characterSheetView: CharacterSheetView
                                        )(implicit executionContext: ExecutionContext) extends BaseController {
  def onPageLoad: Action[AnyContent] = (authAction andThen getUserDataAction).async {implicit request: AuthenticatedUserRequest[AnyContent] =>
    characterSheetRepository.getSheetByUser(request.username).map {
      sheet =>
        sheet.map(sheet => Ok(characterSheetView(sheet))).getOrElse(Unauthorized("can't find it"))
    }
  }
}
