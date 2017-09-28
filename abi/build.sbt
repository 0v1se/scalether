name := "abi"

libraryDependencies += "org.bouncycastle" % "bcprov-jdk15on" % "1.58"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"
)

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"