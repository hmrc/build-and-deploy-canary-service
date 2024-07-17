import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings

lazy val microservice = Project("build-and-deploy-canary-service", file("."))
  .enablePlugins(PlayScala, SbtDistributablesPlugin)
  .settings(
    majorVersion              := 0,
    scalaVersion              := "3.3.3",
    libraryDependencies       ++= AppDependencies.compile ++ AppDependencies.test,
    TwirlKeys.templateImports ++= Seq(
      "uk.gov.hmrc.buildanddeploycanaryservice.config.AppConfig",
      "uk.gov.hmrc.govukfrontend.views.html.components._",
      "uk.gov.hmrc.hmrcfrontend.views.html.components._",
      "uk.gov.hmrc.hmrcfrontend.views.html.helpers._"
    ),
    scalacOptions += "-Wconf:cat=unused-imports&src=html/.*:s",
    scalacOptions += "-Wconf:cat=unused-imports&src=routes/.*:s",
    Test / javaOptions ++= Seq("--add-opens", "java.base/java.util=ALL-UNNAMED") // required for reflection in EnvFixture
  )
  .settings(resolvers += Resolver.jcenterRepo)
