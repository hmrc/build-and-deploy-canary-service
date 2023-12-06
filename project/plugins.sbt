resolvers += "HMRC-open-artefacts-maven" at "https://open.artefacts.tax.service.gov.uk/maven2"
resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)
resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("uk.gov.hmrc"       % "sbt-auto-build"     % "3.15.0")
addSbtPlugin("uk.gov.hmrc"       % "sbt-distributables" % "2.4.0")
addSbtPlugin("org.playframework" % "sbt-plugin"         % "3.0.0")
addSbtPlugin("org.irundaia.sbt"  % "sbt-sassify"        % "1.5.1")
