
lazy val `lecture-5-polymorphism` = project
  .in(file("."))
  .settings(
    version in ThisBuild := "0.0.1",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.0" % Test
    )
  )