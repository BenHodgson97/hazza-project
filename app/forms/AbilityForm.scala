package forms

import models.AbilityQuery
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

class AbilityForm {
  def form: Form[AbilityQuery] = Form[AbilityQuery] (
    mapping(
      "search-bar" -> nonEmptyText
    )(AbilityQuery.apply)(AbilityQuery.unapply)
  )
}
