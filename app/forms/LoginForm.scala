package forms

import play.api.data.Form
import play.api.data.Forms._
import models.Login
import play.api.data.validation.Constraints

class LoginForm {
  def form: Form[Login] = Form[Login](
    mapping(
      "username" -> text.verifying(Constraints.nonEmpty)
    )(Login.apply)(Login.unapply)
  )
}
