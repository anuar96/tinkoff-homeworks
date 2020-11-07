lazy val `lecture-7-functional-programming` = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "3.2.2",
      "org.scalatest" %% "scalatest" % "3.2.0" % Test
    )
  )