name := "core"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.0-MF"

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.1",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.1"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"
)

