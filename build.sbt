ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val circeVersion = "0.14.1"

lazy val root = (project in file("."))
  .settings(
    name := "scm",
    idePackagePrefix := Some("de.htwg.scm"),
    libraryDependencies += "org.scalafx" % "scalafx_2.13" % "20.0.0-R31",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.6.0",
    libraryDependencies += "com.google.inject" % "guice" % "7.0.0",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.3.0-SNAP4" % Test,
    libraryDependencies ++= Seq(
        "io.circe" %% "circe-core",
        "io.circe" %% "circe-generic",
        "io.circe" %% "circe-parser"
    ).map(_ % circeVersion)


  )
