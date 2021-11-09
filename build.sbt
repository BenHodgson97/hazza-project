name := """hazza-project"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "1.0.4-play28",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)

// Adds additional packages into Twirl
TwirlKeys.templateImports += "models.request._"
routesImport += "models.ability.Group"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
