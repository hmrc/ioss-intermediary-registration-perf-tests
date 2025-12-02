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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.registration.RegistrationRequests._

class RegistrationSimulation extends PerformanceTestRunner {

  setup("registration", "IOSS Intermediary Registration Journey") withRequests
    (
      getAuthorityWizard,
      postAuthorityWizard,
      getIOSSIntermediaryRegistered,
      postIOSSIntermediaryRegistered,
      getRegisteredForVatInUk,
      postRegisteredForVatInUk,
      getNiOrEuBased,
      postNiOrEuBased,
      getRegisterToUseService,
      postRegisterToUseService,
      resumeJourney,
      getConfirmVatDetails,
      postConfirmVatDetails,
      getHaveOtherTradingName,
      postHaveOtherTradingName,
      getOtherTradingName(1),
      postOtherTradingName(1, "trading name"),
      getAddTradingName,
      postAddTradingName(true, Some(2)),
      getOtherTradingName(2),
      postOtherTradingName(2, "2nd trading name"),
      getAddTradingName,
      postAddTradingName(false, None),
      getHasPreviouslyRegisteredAsIntermediary,
      postHasPreviouslyRegisteredAsIntermediary(1),
      getPreviousEuCountry(1),
      postPreviousEuCountry(1, "AT"),
      getPreviousIntermediaryRegistrationNumber(1),
      postPreviousIntermediaryRegistrationNumber(1, "IN0407777777"),
      getAddPreviousIntermediaryRegistration,
      postAddPreviousIntermediaryRegistration(true, Some(2)),
      getPreviousEuCountry(2),
      postPreviousEuCountry(2, "HR"),
      getPreviousIntermediaryRegistrationNumber(2),
      postPreviousIntermediaryRegistrationNumber(2, "IN1919876543"),
      getAddPreviousIntermediaryRegistration,
      postAddPreviousIntermediaryRegistration(false, None),
      getEuFixedEstablishment,
      postEuFixedEstablishment(1),
      getEuFixedEstablishmentCountry(1),
      postEuFixedEstablishmentCountry(1, "AT"),
      getTradingNameBusinessAddress(1),
      postTradingNameBusinessAddress(1),
      getRegistrationType(1),
      postRegistrationType(1, "vatNumber"),
      getEuVatNumber(1),
      postEuVatNumber(1, "ATU88882211"),
      getCheckTaxDetails(1),
      postCheckTaxDetails(1),
      getAddTaxDetails,
      postAddTaxDetails(true, Some(2)),
      getEuFixedEstablishmentCountry(2),
      postEuFixedEstablishmentCountry(2, "NL"),
      getTradingNameBusinessAddress(2),
      postTradingNameBusinessAddress(2),
      getRegistrationType(2),
      postRegistrationType(2, "taxId"),
      getEuTaxReference(2),
      postEuTaxReference(2, "NL12345678ABC"),
      getCheckTaxDetails(2),
      postCheckTaxDetails(2),
      getAddTaxDetails,
      postAddTaxDetails(false, None),
      getContactDetails,
      postContactDetails,
      getBankAccountDetails,
      postBankAccountDetails,
      getCheckYourAnswers,
      postCheckYourAnswers,
      getRegistrationSuccessful
    )

  setup("amendRegistration", "IOSS Intermediary Amend Registration Journey") withRequests
    (
      getAuthorityWizard,
      postAuthorityWizardIntNumber("IN9001234568", "start-amend-journey"),
      getAmendJourney,
      getAmendAddTradingName,
      postAmendAddTradingName(true),
      getAmendTradingName(3),
      postAmendTradingName(3, "3rd trading name amend"),
      getAmendAddTradingName,
      postAmendAddTradingName(false),
      getChangeYourRegistration,
      postChangeYourRegistration,
      getSuccessfulAmend
    )

  setup("rejoinRegistration", "IOSS Intermediary Rejoin Registration Journey") withRequests
    (
      getAuthorityWizard,
      postAuthorityWizardIntNumber("IN9001113232", "start-amend-journey"),
      getRejoinJourney,
      getRejoinAddTradingName,
      postRejoinAddTradingName(true),
      getRejoinTradingName(3),
      postRejoinTradingName(3, "3rd trading name rejoin"),
      getRejoinAddTradingName,
      postRejoinAddTradingName(false),
      getRejoinCheckYourDetails,
      postRejoinCheckYourDetails,
      getSuccessfulRejoin
    )

  setup("multiplePreviousRegistrations", "IOSS Intermediary Multiple Previous Registrations Journey") withRequests
    (
      getAuthorityWizard,
      postAuthorityWizardWithMultipleIntEnrolments("start-amend-journey"),
      getAmendJourney,
      getChangePreviousRegistrations,
      postChangePreviousRegistrations("IN9007230002"),
      getAmendBusinessContactDetails,
      postAmendBusinessContactDetails("change-a-previous-registration"),
      getChangeAPreviousRegistration,
      postChangeAPreviousRegistration,
      getAmendJourney,
      getChangePreviousRegistrations,
      postChangePreviousRegistrations("IN9008230002"),
      getAmendBankDetails,
      postAmendBankDetails("change-a-previous-registration"),
      getChangeAPreviousRegistration,
      postChangeAPreviousRegistration
    )

  runSimulation()
}
