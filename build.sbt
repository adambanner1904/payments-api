name := """payments-api"""
organization := "com.adam"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.8.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.adam.binders._"
