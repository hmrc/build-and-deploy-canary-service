import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "build-and-deploy-canary-service"

val silencerVersion = "1.7.14"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    majorVersion                     := 0,
    scalaVersion                     := "2.13.12",
    libraryDependencies              ++= AppDependencies.compile ++ AppDependencies.test,
    TwirlKeys.templateImports ++= Seq(
      "uk.gov.hmrc.buildanddeploycanaryservice.config.AppConfig",
      "uk.gov.hmrc.govukfrontend.views.html.components._",
      "uk.gov.hmrc.hmrcfrontend.views.html.components._",
      "uk.gov.hmrc.hmrcfrontend.views.html.helpers._"
    ),
    scalacOptions += "-Wconf:cat=unused-imports&src=html/.*:s", 
    scalacOptions += "-Wconf:cat=unused-imports&src=routes/.*:s"
  )
  .configs(IntegrationTest)
  .settings(integrationTestSettings(): _*)
  .settings(resolvers += Resolver.jcenterRepo)
  .settings(isPublicArtefact := false)
