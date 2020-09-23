
lazy val `lecture-3-pattern-match-adt` = project
  .in(file("."))
  .settings(
    version in ThisBuild := "0.0.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % Test
  )