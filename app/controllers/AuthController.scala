package controllers
import com.google.inject.{Inject, Singleton}
import forms.LoginForm
import models.request.AuthenticatedRequest
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import views.html.LoginView


@Singleton
class AuthController @Inject()(val controllerComponents: ControllerComponents,
                               loginView: LoginView,
                               loginForm: LoginForm) extends BaseController {
  def onPageLoad: Action[AnyContent] = Action {
    request =>
      implicit val authenticatedRequest: AuthenticatedRequest[AnyContent] = new AuthenticatedRequest[AnyContent](request, "default")
      Ok(loginView())
  }

  def onSubmit: Action[AnyContent] = Action {
    implicit request =>
      implicit val authenticatedRequest: AuthenticatedRequest[AnyContent] = new AuthenticatedRequest[AnyContent](request, "default")
      loginForm.form.bindFromRequest().fold(
        hasErrors => BadRequest(loginView()), //TODO Add error messages for bad requests
        login => Redirect(request.session.get("redirectLocation").getOrElse(controllers.routes.DiceRollerController.onPageLoad.url)).withNewSession.addingToSession("username" -> login.username)
      )
  }
}
