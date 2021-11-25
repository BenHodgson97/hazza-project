package forms

import models.Login
import play.api.data.Form
import play.api.data.Forms._

class LoginForm {
  def form: Form[Login] = Form[Login](
    mapping(
      "username" -> nonEmptyText.transform[String](_.trim, identity)
    )(Login.apply)(Login.unapply)
  )
}
