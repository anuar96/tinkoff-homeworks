import sbt.Keys.{scalaVersion, version}


lazy val `scala-fintech-school` = project
  .in(file("."))
  .settings(
    name in ThisBuild := "scala-global-2020",
    version in ThisBuild := "0.1",
    scalaVersion in ThisBuild := "2.13.3",
  )
  .aggregate(
    `lecture-1`,
    `lecture-2-classes-functions`,
    `lecture-3-pattern-match-adt`,
    `lecture-4-collections`,
    `lecture-5-polymorphism`,
    `lecture-6-future`
  )

lazy val `lecture-1` = project
lazy val `lecture-2-classes-functions` = project
lazy val `lecture-3-pattern-match-adt` = project
lazy val `lecture-4-collections` = project
lazy val `lecture-5-polymorphism` = project
lazy val `lecture-6-future` = project