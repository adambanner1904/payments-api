name := """payments-api"""
organization := "com.adam"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.8.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
libraryDependencies += ("org.mongodb.scala" %% "mongo-scala-driver" % "5.1.0").cross(CrossVersion.for3Use2_13)

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.adam.binders._"
