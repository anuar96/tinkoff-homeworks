
lazy val `lecture-5-polymorphism` = project
  .in(file("."))
  .settings(
    version in ThisBuild := "0.0.1",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.0" % Test
    )
  )