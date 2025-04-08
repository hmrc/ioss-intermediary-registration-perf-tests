/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.registration

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object RegistrationRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("ioss-intermediary-registration-frontend")
  val route: String   = "/intermediary-ioss"

  val navigateToHomePage: HttpRequestBuilder =
    http("Navigate to Home Page")
      .get(s"$baseUrl$route")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

}
