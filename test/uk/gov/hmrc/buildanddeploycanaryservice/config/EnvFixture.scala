/*
 * Copyright 2024 HM Revenue & Customs
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

import org.scalatest.{BeforeAndAfterEach, Suite}

import scala.collection.JavaConverters._
import scala.reflect.ClassTag
import scala.util.Try

trait EnvFixture extends BeforeAndAfterEach {
  this: Suite =>

  private val mutableEnvVars = new AsModifiable(System.getenv())

  private var keysBefore: Set[String] = _

  override protected def beforeEach(): Unit = {
    this.keysBefore = mutableEnvVars.keys
    super.beforeEach()
  }

  override protected def afterEach(): Unit = {
    try super.afterEach()
    finally {
      val keysAfter = mutableEnvVars.keys
      val keysToRemove = keysAfter -- keysBefore
      for {
        k <- keysToRemove
      } mutableEnvVars.remove(k)
    }
  }

  def setEnv(key: String, value: String): Unit =
    mutableEnvVars.put(key, value)

  private abstract class MapHandler {
    protected def m: java.util.Map[String, String]

    final def keys: Set[String] =
      m.keySet.asScala.toSet

    final def put(k: String, v: String): Unit =
      m.put(k, v)

    final def remove(k: String): Unit =
      m.remove(k)
  }

  private class AsModifiable(unmodifiableMap: java.util.Map[String, String]) extends MapHandler {
    override protected val m: java.util.Map[String, String] = {
      val field = unmodifiableMap.getClass.getDeclaredField("m")
      field.setAccessible(true)
      field.get(unmodifiableMap).asInstanceOf[java.util.Map[String, String]]
    }
  }
}
