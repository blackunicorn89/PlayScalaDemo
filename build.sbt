import scala.collection.JavaConverters._

name := """PlayScalaDemo"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

resolvers += "org.serial" at "https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(
  jdbc,
  "org.xerial" % "sqlite-jdbc" % "3.36.0.3",
  "org.playframework.anorm" %% "anorm" % "2.6.10"
)



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
