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

  val loginUrl = baseUrlFor("auth-login-stub")

  def inputSelectorByName(name: String): Expression[String] = s"input[name='$name']"

  def getAuthorityWizard =
    http("Get Authority Wizard page")
      .get(loginUrl + s"/auth-login-stub/gg-sign-in")
      .check(status.in(200, 303))

  def postAuthorityWizard =
    http("Enter Auth login credentials ")
      .post(loginUrl + s"/auth-login-stub/gg-sign-in")
      .formParam("authorityId", "")
      .formParam("gatewayToken", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("email", "user@test.com")
      .formParam("credentialRole", "User")
      .formParam("redirectionUrl", baseUrl + route)
      .formParam("enrolment[0].name", "HMRC-MTD-VAT")
      .formParam("enrolment[0].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[0].taxIdentifier[0].value", "100000001")
      .formParam("enrolment[0].state", "Activated")
      .check(status.in(200, 303))
      .check(headerRegex("Set-Cookie", """mdtp=(.*)""").saveAs("mdtpCookie"))

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

  def getRegisterToUseService =
    http("Get Register To Use Service page")
      .get(s"$baseUrl$route/register-to-use-service")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postRegisterToUseService =
    http("Post Register To Use Service page")
      .post(s"$baseUrl$route/register-to-use-service")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(303))
      .check(header("Location").is(s"$route/on-sign-in"))

  def resumeJourney =
    http("Resume journey")
      .get(s"$baseUrl$route/on-sign-in")
      .check(status.in(303))

  def getConfirmVatDetails =
    http("Get Confirm VAT Details page")
      .get(s"$baseUrl$route/confirm-vat-details")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postConfirmVatDetails =
    http("Post Confirm VAT Details page")
      .post(s"$baseUrl$route/confirm-vat-details")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "yes")
      .check(status.in(303))
      .check(header("Location").is(s"$route/have-uk-trading-name"))

  def getHaveUkTradingName =
    http("Get Have UK Trading Name page")
      .get(s"$baseUrl$route/have-uk-trading-name")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postHaveUkTradingName =
    http("Post Have UK Trading Name page")
      .post(s"$baseUrl$route/have-uk-trading-name")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(303))
      .check(header("Location").is(s"$route/uk-trading-name/1"))

  def getUkTradingName(index: Int) =
    http("Get UK Trading Name page")
      .get(s"$baseUrl$route/uk-trading-name/$index")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postUkTradingName(index: Int, tradingName: String) =
    http("Post UK Trading Name page")
      .post(s"$baseUrl$route/uk-trading-name/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", tradingName)
      .check(status.in(303))
      .check(header("Location").is(s"$route/add-uk-trading-name"))

  def getAddTradingName =
    http("Get Add Trading Name page")
      .get(s"$baseUrl$route/add-uk-trading-name")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def testAddTradingName(answer: Boolean) =
    http("Add Trading Name")
      .post(s"$baseUrl$route/add-uk-trading-name")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def postAddTradingName(answer: Boolean, index: Option[Int]) =
    if (answer) {
      testAddTradingName(answer)
        .check(header("Location").is(s"$route/uk-trading-name/${index.get}"))
    } else {
      testAddTradingName(answer)
      //      not developed yet
//        .check(header("Location").is(s"$route/previous-oss"))
    }

  def getIsTaxRegisteredInEu =
    http("Get Is Tax Registered in EU page")
      .get(s"$baseUrl$route/tax-in-eu")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postIsTaxRegisteredInEu(index: Int) =
    http("Answer Is Tax Registered in EU")
      .post(s"$baseUrl$route/tax-in-eu")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "true")
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/eu-tax/$index"))

  def getVatRegisteredInEuMemberState(index: Int) =
    http("Get Tax Registered in EU Member State page")
      .get(s"$baseUrl$route/eu-tax/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatRegisteredInEuMemberState(index: Int, countryCode: String) =
    http("Enter Tax Registered in EU Member State")
      .post(s"$baseUrl$route/eu-tax/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/eu-fixed-establishment/$index"))

  def getHowDoYouOperate(index: Int) =
    http("Get How Do You Operate page")
      .get(s"$baseUrl$route/eu-fixed-establishment/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postHowDoYouOperate(index: Int) =
    http("Answer How Do You Operate Page")
      .post(s"$baseUrl$route/eu-fixed-establishment/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/registration-tax-type/$index"))

  def getRegistrationType(index: Int) =
    http("Get Registration Type page")
      .get(s"$baseUrl$route/registration-tax-type/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def testRegistrationType(index: Int, registrationType: String) =
    http("Answer Registration Type Page")
      .post(s"$baseUrl$route/registration-tax-type/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", registrationType)
      .check(status.in(200, 303))

  def postRegistrationType(index: Int, registrationType: String) =
    if (registrationType == "vatNumber") {
      testRegistrationType(index, registrationType)
        .check(header("Location").is(s"$route/eu-vat-number/$index"))
    } else {
      testRegistrationType(index, registrationType)
        .check(header("Location").is(s"$route/eu-tax-identification-number/$index"))
    }

  def getEuVatNumber(index: Int) =
    http("Get EU VAT Number page")
      .get(s"$baseUrl$route/eu-vat-number/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postEuVatNumber(index: Int, euVatNumber: String) =
    http("Enter EU VAT Number")
      .post(s"$baseUrl$route/eu-vat-number/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", euVatNumber)
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/eu-trading-name/$index"))

  def getFixedEuTradingName(index: Int) =
    http("Get Fixed Establishment Trading Name page")
      .get(s"$baseUrl$route/eu-trading-name/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postFixedEuTradingName(index: Int, tradingName: String) =
    http("Enter Fixed Eu Trading Name")
      .post(s"$baseUrl$route/eu-trading-name/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", tradingName)
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/eu-fixed-establishment-address/$index"))

  def getFixedEstablishmentAddress(index: Int) =
    http("Get Fixed Establishment Address page")
      .get(s"$baseUrl$route/eu-fixed-establishment-address/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postFixedEstablishmentAddress(index: Int) =
    http("Enter Fixed Establishment Address")
      .post(s"$baseUrl$route/eu-fixed-establishment-address/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("line1", "line1")
      .formParam("line2", "line2")
      .formParam("townOrCity", "townOrCity")
      .formParam("postCode", "ABC")
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/check-tax-details/$index"))

  def getCheckTaxDetails(index: Int) =
    http("Get Check Tax Details page")
      .get(s"$baseUrl$route/check-tax-details/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckTaxDetails(index: Int) =
    http("Submit Check EU VAT Details")
      .post(s"$baseUrl$route/check-tax-details/$index?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/add-tax-details"))

  def getAddTaxDetails =
    http("Get Add VAT Details page")
      .get(s"$baseUrl$route/add-tax-details")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def testAddTaxDetails(answer: Boolean) =
    http("Answer Add EU VAT Details")
      .post(s"$baseUrl$route/add-tax-details?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def postAddTaxDetails(answer: Boolean, index: Option[Int]) =
    if (answer) {
      testAddTaxDetails(answer)
        .check(header("Location").is(s"$route/eu-tax/${index.get}"))
    } else {
      testAddTaxDetails(answer)
        .check(header("Location").is(s"$route/contact-details"))
    }

  def getEuTaxReference(index: Int) =
    http("Get EU Tax Reference page")
      .get(s"$baseUrl$route/eu-tax-identification-number/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postEuTaxReference(index: Int, taxReference: String) =
    http("Enter EU Tax Reference")
      .post(s"$baseUrl$route/eu-tax-identification-number/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", taxReference)
      .check(status.in(200, 303))
      .check(header("Location").is(s"$route/eu-trading-name/$index"))

  def getContactDetails =
    http("Get Contact Details page")
      .get(s"$baseUrl$route/contact-details")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postContactDetails =
    http("Enter Contact Details")
      .post(s"$baseUrl$route/contact-details")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("fullName", "Trader Name")
      .formParam("telephoneNumber", "012301230123")
      .formParam("emailAddress", "trader@testemail.com")
      .check(status.in(200, 303))
//       next page not developed yet
//      .check(header("Location").is(s"$route/bank-account-details"))

}
