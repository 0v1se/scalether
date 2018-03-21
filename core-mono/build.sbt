resolvers += Resolver.bintrayRepo("daonomic", "maven")

libraryDependencies += "io.daonomic.rpc" %% "transport-mono" % Versions.scalaRpc
libraryDependencies += "io.daonomic.cats" %% "mono" % Versions.catsMono