package forms

import models.AbilityQuery
import models.ability.Group
import models.ability.Group.fromString
import play.api.data.{Form, Mapping}
import play.api.data.Forms.{mapping, nonEmptyText, optional, seq, text}

class AbilityForm {
  def searchMapping: Mapping[Option[String]] = optional(text).transform(_.filter(_.nonEmpty), identity)

  def groupMapping: Mapping[Option[Seq[Group]]] = seq(nonEmptyText).transform(str => { println(str)
    Option(str.map(Group.fromString)).filter(_.nonEmpty)
  }, filters => filters.getOrElse(Nil).map(_.toString))

  def form: Form[AbilityQuery] = Form[AbilityQuery] (
    mapping(
      "search-bar" -> searchMapping,
      "group-filter" -> groupMapping
    )(AbilityQuery.apply)(AbilityQuery.unapply)
  )
}
