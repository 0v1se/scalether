name := "scalether"

parallelExecution in ThisBuild := false

def base(project: Project): Project = project.settings(
  organization := "io.daonomic.scalether",
  bintrayPackage := s"scalether-${name.value}",
  bintrayOrganization := Some("daonomic"),
  bintrayPackageLabels := Seq("daonomic", "rpc", "scala", "scalether", "ethereum"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  version := "0.1.4",
  scalaVersion := Versions.scala
)

def tests(project: Project): Project = project
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % Versions.scalatest % "test")
  .settings(libraryDependencies += "org.scalacheck" %% "scalacheck" % Versions.scalacheck % "test")
  .settings(libraryDependencies += "org.mockito" % "mockito-all" % Versions.mockito)

def common(project: Project): Project = base(project)
  .dependsOn(domain, `test-common` % "test")

lazy val util = tests(base(project))

lazy val domain = tests(base(project))
  .dependsOn(util)

lazy val `test-common` = base(project)
  .dependsOn(domain)
  .settings(publish := {})

lazy val core = common(project)
  .dependsOn(util)

lazy val `core-mono` = common(project)
  .dependsOn(core)

lazy val abi = common(project)
  .dependsOn(core)

lazy val transaction = common(project)
  .dependsOn(core)

lazy val `transaction-mono` = common(project)
  .dependsOn(transaction, `core-mono`)

lazy val listener = common(project)
  .dependsOn(core)

lazy val `listener-mono` = common(project)
  .dependsOn(listener, `core-mono`)

lazy val contract = common(project)
  .dependsOn(abi, transaction)

lazy val `contract-mono` = common(project)
  .dependsOn(contract, `transaction-mono`)

lazy val test = common(project)
  .dependsOn(contract, util, listener)
  .settings(publish := {})

lazy val generator = base(project)
  .dependsOn(`test-common` % "test")

lazy val scalether = base(project in file("."))
  .settings(publish := {})
  .aggregate(util, domain, core, `core-mono`, abi, contract, `contract-mono`, transaction, `transaction-mono`, listener, `listener-mono`, generator, test)
