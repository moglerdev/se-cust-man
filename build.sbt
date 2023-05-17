val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "seCustMan",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    coverageExcludedPackages := "*Main.scala",
    libraryDependencies += "org.scalatestplus" %% "mockito-4-6" % "3.2.15.0" % "test",
    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.5.0-M1",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.6.0"
)
