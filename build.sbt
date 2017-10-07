name := "scalether"

version := "0.1-SNAPSHOT"

organization := "org.scalether"

scalaVersion := "2.12.3"

def common(project: Project): Project =
  project.settings(organization := "org.scalether")
    .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test")
    .settings(libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" % "test")
    .settings(libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test")

lazy val util = common(project)

lazy val core = common(project).dependsOn(util)

lazy val abi = common(project).dependsOn(core)

lazy val extra = common(project).dependsOn(core)

lazy val contract = common(project).dependsOn(abi, extra)

lazy val `async-http-client` = common(project).dependsOn(core)

lazy val `scalaj-http` = common(project).dependsOn(core)

lazy val test = common(project).dependsOn(contract, `scalaj-http`)

lazy val generator = common(project).dependsOn(abi)

lazy val root = (project in file(".")).
  aggregate(util, core, abi, contract, extra, `async-http-client`, `scalaj-http`, generator)

