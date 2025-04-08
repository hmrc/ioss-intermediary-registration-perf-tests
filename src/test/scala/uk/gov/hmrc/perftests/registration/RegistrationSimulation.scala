/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.registration

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.registration.RegistrationRequests._

class RegistrationSimulation extends PerformanceTestRunner {

  setup("registration", "IOSS Intermediary Registration Journey") withRequests
    navigateToHomePage

  runSimulation()
}
