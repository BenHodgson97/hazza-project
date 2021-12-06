package models

import models.ability.Group

case class AbilityQuery(search: Option[String], groupFilter: Option[Seq[Group]]) {
  val searchToRegex: Option[String] = search.map(search => "^.*" + search.replace(" ", ".*") + ".*$")
}
