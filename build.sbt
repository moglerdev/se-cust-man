import scoverage.ScoverageKeys.*

version := "1.0.0"
scalaVersion := "3.2.2"

val circeVersion = "0.14.1"

scoverage.ScoverageKeys.coverageExcludedPackages := ".*.gui.*"
scoverage.ScoverageKeys.coverageExcludedPackages := ".*.gui.*"
scoverage.ScoverageSbtPlugin.autoImport.coverageExcludedPackages := ".*.gui.*"
scoverage.ScoverageSbtPlugin.autoImport.coverageExcludedPackages := ".*.gui.*"
scoverage.ScoverageKeys.coverageEnabled := true

name := "scm"

idePackagePrefix := Some("de.htwg.scm")

libraryDependencies += "org.scalafx" % "scalafx_2.13" % "20.0.0-R31"
libraryDependencies += "org.postgresql" % "postgresql" % "42.6.0"
libraryDependencies += "com.google.inject" % "guice" % "7.0.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser").map(_ % circeVersion)

libraryDependencies += "io.github.cdimascio" % "java-dotenv" % "5.2.2"
libraryDependencies += "org.mockito" % "mockito-core" % "5.4.0" % Test
libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test
