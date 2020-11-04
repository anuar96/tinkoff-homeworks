lazy val `lecture-8-concurrency` = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.0" % Test
    ),
    fork in Test := true
  )