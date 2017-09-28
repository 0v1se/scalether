name := "scalether"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.3"

def common(project: Project): Project = {
  project.settings(organization := "org.scalether")
}

lazy val core = common(project)

lazy val abi = common(project.dependsOn(core))

lazy val contract = common(project.dependsOn(abi))

lazy val util = common(project.dependsOn(core))

lazy val `async-http-client` = common(project.dependsOn(core))

lazy val `scalaj-http` = common(project.dependsOn(core))

lazy val root = common((project in file(".")).
  aggregate(core, abi, contract, `async-http-client`, `scalaj-http`, util))

