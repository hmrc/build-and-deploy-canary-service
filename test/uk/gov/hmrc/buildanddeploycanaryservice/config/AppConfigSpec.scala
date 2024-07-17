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

import org.scalatest.flatspec.AnyFlatSpec
import play.api.{Configuration, Environment}

class AppConfigSpec extends AnyFlatSpec with EnvFixture:
  private val env = Environment.simple()

  it should "load config okay when required settings are present" in {
    setEnv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "test")

    val configuration =
      Configuration.load(env)
        .withFallback(Configuration("service.will.fail.to.start.without.this.sys.prop" -> "super-important"))
        .withFallback(Configuration("base64.string.with.quotes.stripped" -> "ZGVhZGJlZWYK"))

    val appConfig = AppConfig(configuration)

    assert(System.getenv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR") === "test")
    assert(appConfig.requiredEnvVar === Some("test"))
  }

  it should "throw an exception when the required env var is not set" in {
    val configuration =
      Configuration.load(env)
        .withFallback(Configuration("service.will.fail.to.start.without.this.sys.prop" -> "super-important"))
        .withFallback(Configuration("base64.string.with.quotes.stripped" -> "ZGVhZGJlZWYK"))

    assertThrows[Exception]:
      AppConfig(configuration)
  }

  it should "throw an exception when the required system property is not set" in {
    setEnv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "test")

    val configuration =
      Configuration.load(env)
        .withFallback(Configuration("base64.string.with.quotes.stripped" -> "ZGVhZGJlZWYK"))

    assertThrows[Exception]:
      AppConfig(configuration)
  }

  it should "throw an exception if the base64 property is not decodeable" in {
    setEnv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "test")

    val configuration =
      Configuration.load(env)
        .withFallback(Configuration("base64.string.with.quotes.stripped" -> "'ZGVhZGJlZWYK'"))
        .withFallback(Configuration("service.will.fail.to.start.without.this.sys.prop" -> "super-important"))

    assertThrows[Exception]:
      AppConfig(configuration)
  }

  it should "throw an exception if the cookie deviceId secret property starts with a single quote" in {
    setEnv("SERVICE_WILL_FAIL_TO_START_WITHOUT_THIS_ENV_VAR", "test")

    val configuration =
      Configuration("cookie.deviceId.secret" -> "'bad'")
        .withFallback(Configuration.load(env))
        .withFallback(Configuration("base64.string.with.quotes.stripped" -> "ZGVhZGJlZWYK"))
        .withFallback(Configuration("service.will.fail.to.start.without.this.sys.prop" -> "super-important"))

    assertThrows[Exception]:
      AppConfig(configuration)
  }
