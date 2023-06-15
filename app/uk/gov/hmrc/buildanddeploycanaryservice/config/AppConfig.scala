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

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import play.api.i18n.Lang

@Singleton
class AppConfig @Inject()(config: Configuration, servicesConfig: ServicesConfig) {
  val welshLanguageSupportEnabled: Boolean = config.getOptional[Boolean]("features.welsh-language-support").getOrElse(false)

  val en: String            = "en"
  val cy: String            = "cy"
  val defaultLanguage: Lang = Lang(en)

  val myString: String = config.getOptional[String]("some.config.key").getOrElse("")
  val shouldFail: String = config.getOptional[String]("another.config.key").getOrElse("false")
  val requiredEnvVar: String = sys.env.getOrElse("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "")
  val requiredSystemProperty: String = config.getOptional[String]("service.will.fail.to.start.without.this.sys.prop").getOrElse("")

  if (requiredEnvVar == "") {
    throw new Exception
  }

  if (requiredSystemProperty == "") {
    throw new Exception
  }
}
