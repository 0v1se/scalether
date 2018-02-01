//plain java project so there is no need to include scala-library in maven plugin

libraryDependencies += "org.freemarker" % "freemarker" % Versions.freemarker
libraryDependencies += "commons-beanutils" % "commons-beanutils" % Versions.beanutils
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % Versions.jackson

libraryDependencies += "org.scala-lang" % "scala-library" % Versions.scala % "test"

autoScalaLibrary := false
crossPaths := false