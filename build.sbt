name := "scalether"

version := "0.1-SNAPSHOT"

organization := "org.scalether"

scalaVersion := "2.12.3"

def org(project: Project): Project = project
  .settings(organization := "org.scalether")

def common(project: Project): Project = org(project)
  .dependsOn(domain, `test-common` % "test")

lazy val util = org(project)

lazy val domain = org(project)
  .dependsOn(util)

lazy val `test-common` = org(project)
  .dependsOn(domain)

lazy val core = common(project)
  .dependsOn(util)

lazy val abi = common(project)
  .dependsOn(core)

lazy val extra = common(project)
  .dependsOn(core)

lazy val contract = common(project)
  .dependsOn(abi, extra)

lazy val `async-http-client` = common(project)
  .dependsOn(core)

lazy val `scalaj-http` = common(project)
  .dependsOn(core)

lazy val test = common(project)
  .dependsOn(contract, `scalaj-http`)

lazy val generator = common(project)
  .dependsOn(abi)

lazy val java = common(project)
  .dependsOn(abi, extra)

lazy val root = (project in file(".")).
  aggregate(util, core, abi, contract, extra, `async-http-client`, `scalaj-http`, generator, java)

