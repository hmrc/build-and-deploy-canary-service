import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val bootstrapPlayVersion = "6.3.0"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapPlayVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc"         % "3.22.0-play-28",
    "uk.gov.hmrc" %% "play-language"              % "5.3.0-play-28"
  )

  val test = Seq(
    "uk.gov.hmrc" %% "bootstrap-test-play-28"     % bootstrapPlayVersion  % Test,
    "org.jsoup"   %  "jsoup"                      % "1.13.1"              % Test
  )
}
