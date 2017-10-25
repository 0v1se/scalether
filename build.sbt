import java.util.Properties
val nexusProperties = settingKey[Properties]("Nexus properties")

name := "scalether"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.3"

def base(project: Project): Project = project
  .settings(organization := "org.scalether")
  .settings(nexusProperties := {
    val prop = new Properties()
    IO.load(prop, Path.userHome / ".ivy2" / ".nexus")
    prop
  })
  .settings(publishTo := {
    val nexus = nexusProperties.value.getProperty("url")
    if (isSnapshot.value)
      Some("snapshots" at nexus + "/content/repositories/snapshots/")
    else
      Some("releases" at nexus + "/content/repositories/releases/")
  })
  .settings(credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"))

def common(project: Project): Project = base(project)
  .dependsOn(domain, `test-common` % "test")

lazy val util = base(project)

lazy val domain = base(project)
  .dependsOn(util)

lazy val `test-common` = base(project)
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

lazy val `java-compat` = common(project)
  .dependsOn(abi, extra)

lazy val root = base(project in file(".")).
  aggregate(util, domain, core, abi, contract, extra, `async-http-client`, `scalaj-http`, generator, `java-compat`, test)
