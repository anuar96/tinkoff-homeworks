
lazy val `lecture-4-collections` = project
  .in(file("."))
  .settings(
    version in ThisBuild := "0.0.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % Test
  )