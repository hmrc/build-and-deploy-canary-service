/*
 * Copyright 2022 HM Revenue & Customs
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

import org.scalatest.flatspec.AnyFlatSpec
import play.api.{Configuration, Environment}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.buildanddeploycanaryservice.config.AppConfig
import za.co.absa.commons.scalatest.EnvFixture

class AppConfigSpec extends AnyFlatSpec with EnvFixture {
  private val env           = Environment.simple()
  private val configuration = Configuration.load(env)
  private val serviceConfig = new ServicesConfig(configuration)

  private var appConfig: AppConfig = _

  it should "load config okay when required settings are present" in {
    setEnv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "test")

    appConfig = new AppConfig(configuration, serviceConfig)

    assert("test" === System.getenv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR"))
    assert("test" === appConfig.requiredEnvVar)
  }

  it should "throw an exception when the required env var is not set" in {
    assertThrows[Exception] {
        new AppConfig(configuration, serviceConfig)
    }
  }
}
