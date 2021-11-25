package models

case class AbilityQuery(search: String) {
  val searchToRegex: String = "^.*" + search.replace(" ", ".*") + ".*$"
}
