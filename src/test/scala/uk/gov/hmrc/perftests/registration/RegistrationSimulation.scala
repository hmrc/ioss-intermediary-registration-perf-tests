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
      getHaveUkTradingName,
      postHaveUkTradingName,
      getUkTradingName(1),
      postUkTradingName(1, "trading name"),
      getAddTradingName,
      postAddTradingName(true, Some(2)),
      getUkTradingName(2),
      postUkTradingName(2, "2nd trading name"),
      getAddTradingName,
      postAddTradingName(false, None),
      getIsTaxRegisteredInEu,
      postIsTaxRegisteredInEu(1),
      getVatRegisteredInEuMemberState(1),
      postVatRegisteredInEuMemberState(1, "AT"),
      getHowDoYouOperate(1),
      postHowDoYouOperate(1),
      getRegistrationType(1),
      postRegistrationType(1, "vatNumber"),
      getEuVatNumber(1),
      postEuVatNumber(1, "ATU88882211"),
      getFixedEuTradingName(1),
      postFixedEuTradingName(1, "Austrian Goods"),
      getFixedEstablishmentAddress(1),
      postFixedEstablishmentAddress(1),
      getCheckTaxDetails(1),
      postCheckTaxDetails(1),
      getAddTaxDetails,
      postAddTaxDetails(true, Some(2)),
      getVatRegisteredInEuMemberState(2),
      postVatRegisteredInEuMemberState(2, "NL"),
      getHowDoYouOperate(2),
      postHowDoYouOperate(2),
      getRegistrationType(2),
      postRegistrationType(2, "taxId"),
      getEuTaxReference(2),
      postEuTaxReference(2, "NL12345678ABC"),
      getFixedEuTradingName(2),
      postFixedEuTradingName(2, "Netherlands Trading"),
      getFixedEstablishmentAddress(2),
      postFixedEstablishmentAddress(2),
      getCheckTaxDetails(2),
      postCheckTaxDetails(2),
      getAddTaxDetails,
      postAddTaxDetails(false, Some(1)),
      getContactDetails,
      postContactDetails
    )

  runSimulation()
}
