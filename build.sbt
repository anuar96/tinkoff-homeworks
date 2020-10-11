name := "scala-global-2020"

version := "0.1"

scalaVersion := "2.13.3"

lazy val root = (project in file("."))
  .aggregate(
    `lesson-01`, 
    `lecture-2-classes-functions`, 
    `lecture-3-pattern-match-adt`,
    `lecture-4-collections`
  )

lazy val `lesson-01` = (project in file("lesson-01"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % Test
  )

lazy val `lecture-2-classes-functions` = project
lazy val `lecture-3-pattern-match-adt` = project
lazy val `lecture-4-collections` = project