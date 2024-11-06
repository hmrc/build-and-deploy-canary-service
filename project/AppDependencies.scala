import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val bootstrapPlayVersion = "9.5.0"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-30" % bootstrapPlayVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc-play-30" % "11.3.0"
  )

  val test = Seq(
    "uk.gov.hmrc" %% "bootstrap-test-play-30"     % bootstrapPlayVersion % Test,
    "org.jsoup"   %  "jsoup"                      % "1.17.1"             % Test
  )
}
