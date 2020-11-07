lazy val `lecture-9-rdbms` = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.3.3",
      "org.scalatest" %% "scalatest" % "3.2.0" % Test,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
      "com.h2database" % "h2" % "1.4.200"
    )
  )