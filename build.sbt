name := "es-playground"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.6" % "test"
)
