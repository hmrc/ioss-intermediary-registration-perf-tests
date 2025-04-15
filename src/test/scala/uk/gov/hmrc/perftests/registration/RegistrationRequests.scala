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

package uk.gov.hmrc.perftests.registration

import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.Predef._
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object RegistrationRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("ioss-intermediary-registration-frontend")
  val route: String   = "/intermediary-ioss"

  def inputSelectorByName(name: String): Expression[String] = s"input[name='$name']"

  def getIOSSIntermediaryRegistered =
    http("Get IOSS Intermediary Registered page")
      .get(s"$baseUrl$route/ioss-intermediary-registered")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postIOSSIntermediaryRegistered =
    http("Post IOSS Intermediary Registered page")
      .post(s"$baseUrl$route/ioss-intermediary-registered")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", false)
      .check(status.in(303))
      .check(header("Location").is(s"$route/registered-for-vat-in-uk"))

  def getRegisteredForVatInUk =
    http("Get Registered For VAT in UK page")
      .get(s"$baseUrl$route/registered-for-vat-in-uk")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postRegisteredForVatInUk =
    http("Post Registered For VAT in UK page")
      .post(s"$baseUrl$route/registered-for-vat-in-uk")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(303))
      .check(header("Location").is(s"$route/ni-or-eu-based"))

  def getNiOrEuBased =
    http("Get NI or EU Based page")
      .get(s"$baseUrl$route/ni-or-eu-based")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postNiOrEuBased =
    http("Post NI or EU Based page")
      .post(s"$baseUrl$route/ni-or-eu-based")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(303))
      .check(header("Location").is(s"$route/register-to-use-service"))

}
