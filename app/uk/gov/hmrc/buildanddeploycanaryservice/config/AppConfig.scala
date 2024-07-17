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

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(config: Configuration):

  val welshLanguageSupportEnabled: Boolean =
    config.getOptional[Boolean]("features.welsh-language-support").getOrElse(false)

  val en: String            = "en"
  val cy: String            = "cy"
  val defaultLanguage: Lang = Lang(en)

  val requiredEnvVar: String =
    sys.env.getOrElse("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "")

  val requiredSystemProperty: String =
    config.getOptional[String]("service.will.fail.to.start.without.this.sys.prop").getOrElse("")

  if (requiredEnvVar == "")
    throw new Exception

  if (requiredSystemProperty == "")
    throw new Exception


  val someConfigKey: String =
    config.getOptional[String]("some.config.key").getOrElse("")

  val favColour: String =
    config.getOptional[String]("fav.colour").getOrElse("")

  val base64StringWithQuotesStripped: String =
    config.getOptional[String]("base64.string.with.quotes.stripped").getOrElse("")

  if (base64StringWithQuotesStripped == "")
    throw new Exception

  val decodedBase64StringWithQuotesStripped =
    new String(java.util.Base64.getDecoder.decode(base64StringWithQuotesStripped))

  val cookieDeviceIdSecret: String =
    config.getOptional[String]("cookie.deviceId.secret").getOrElse("")

  if (cookieDeviceIdSecret.startsWith("'"))
    throw new Exception
