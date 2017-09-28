name := "async-http-client"

resolvers +=
  "Sonatype OSS Public" at "https://oss.sonatype.org/content/groups/public"

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"
libraryDependencies += "org.asynchttpclient" % "async-http-client" % "2.1.0-SNAPSHOT"
