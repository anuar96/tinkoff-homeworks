name := "scala-global-2020"

version := "0.1"

scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .aggregate(`lesson-01`)

lazy val `lesson-01` = (project in file("lesson-01"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % Test
  )