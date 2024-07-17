/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.buildanddeploycanaryservice.config

import play.api.Configuration
import play.api.i18n.Lang
import com.typesafe.config.ConfigException

import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton
class AppConfig @Inject()(config: Configuration):

  val welshLanguageSupportEnabled: Boolean =
    config.get[Boolean]("features.welsh-language-support")

  val en: String            = "en"
  val cy: String            = "cy"
  val defaultLanguage: Lang = Lang(en)

  val someConfigKey: Option[String] =
    config.getOptional[String]("some.config.key")

  val favColour: Option[String] =
    config.getOptional[String]("fav.colour")

  val assertExpectedConfig: Boolean =
    config.get[Boolean]("assert.expected.config")

  val requiredEnvVarKey = "SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR"
  val requiredEnvVar: Option[String] =
    Some(sys.env.getOrElse(requiredEnvVarKey, ""))
      .filter(_ != "")

  val requiredSystemPropertyKey = "service.will.fail.to.start.without.this.sys.prop"
  val requiredSystemProperty: Option[String] =
    config.getOptional[String](requiredSystemPropertyKey)

  val base64StringWithQuotesStrippedKey = "base64.string.with.quotes.stripped"
  val base64StringWithQuotesStripped: Option[String] =
    config.getOptional[String](base64StringWithQuotesStrippedKey)

  val decodedBase64StringWithQuotesStripped =
    base64StringWithQuotesStripped.flatMap(s =>
      Try(String(java.util.Base64.getDecoder.decode(s))).toOption
    )

  val cookieDeviceIdSecretKey = "cookie.deviceId.secret"
  val cookieDeviceIdSecret: Option[String] =
    config.getOptional[String](cookieDeviceIdSecretKey)

  if assertExpectedConfig then
    val errors = Seq(
      Option(s"env var '$requiredEnvVarKey' is missing").filter(_ => requiredEnvVar.isEmpty),
      Option(s"system property '$requiredSystemPropertyKey' is missing").filter(_ => requiredSystemProperty.isEmpty),
      Option(s"system property '$base64StringWithQuotesStrippedKey' is missing").filter(_ => base64StringWithQuotesStripped.isEmpty),
      Option(s"system property '$base64StringWithQuotesStrippedKey' is not a valid base64 value").filter(_ => decodedBase64StringWithQuotesStripped.isEmpty),
      Option(s"config '$cookieDeviceIdSecretKey' is missing or starts with a quote").filter(_ => cookieDeviceIdSecret.exists(_.startsWith("'")))
    ).flatten

    if errors.nonEmpty then
      throw ConfigException.Generic(s"The following errors were found:\n${errors.map(" - " + _).mkString("\n")}")
