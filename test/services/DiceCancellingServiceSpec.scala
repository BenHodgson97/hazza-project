package services

import models.dice.Symbol._
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

class DiceCancellingServiceSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting {
  val diceCancellingService: DiceCancellingService = inject[DiceCancellingService]
  "cancelSymbols" must {
    "cancel down to nothing" when {
      "the result contains equal amount of success and failure" in {
        diceCancellingService.cancelSymbols(Seq(Success, Failure)) mustEqual Seq()
      }

      "the result contains equal amount of advantage and threat" in {
        diceCancellingService.cancelSymbols(Seq(Advantage, Advantage, Threat, Threat)) mustEqual Seq()
      }
    }

    "count the success in a triumph" when {
      "there is a success and triumph" in {
        diceCancellingService.cancelSymbols(Seq(Success, Triumph)) mustEqual Seq(Success, Success, Triumph)
      }

      "there is just a triumph" in {
        diceCancellingService.cancelSymbols(Seq(Triumph)) mustEqual Seq(Success, Triumph)
      }
    }

    "count the failure in a despair" when {
      "there is a failure and despair" in {
        diceCancellingService.cancelSymbols(Seq(Failure, Despair)) mustEqual Seq(Failure, Failure, Despair)
      }

      "there is just a despair" in {
        diceCancellingService.cancelSymbols(Seq(Despair)) mustEqual Seq(Failure, Despair)
      }
    }

    "not cancel triumphs against despairs" in {
      diceCancellingService.cancelSymbols(Seq(Triumph, Despair)) mustEqual Seq(Triumph, Despair)
    }

    "work out successFailureCount" when {
      "all successes and no failures" in {
        diceCancellingService.cancelSymbols(Seq(Success, Success)) mustEqual Seq(Success, Success)
      }
      "all failures and no successes" in {
        diceCancellingService.cancelSymbols(Seq(Failure, Failure)) mustEqual Seq(Failure, Failure)
      }

      "more successes than failures" in {
        diceCancellingService.cancelSymbols(Seq(Success, Success, Failure)) mustEqual Seq(Success)
      }

      "more failures than successes" in {
        diceCancellingService.cancelSymbols(Seq(Failure, Failure, Success)) mustEqual Seq(Failure)
      }
    }

    "work out advantageThreatCount" when {
      "all advantages and no threats" in {
        diceCancellingService.cancelSymbols(Seq(Advantage, Advantage)) mustEqual Seq(Advantage, Advantage)
      }
      "all threats and no advantages" in {
        diceCancellingService.cancelSymbols(Seq(Threat, Threat)) mustEqual Seq(Threat, Threat)
      }
      "more advantages than threats" in {
        diceCancellingService.cancelSymbols(Seq(Advantage, Advantage, Threat)) mustEqual Seq(Advantage)
      }
      "more threats than failures" in {
        diceCancellingService.cancelSymbols(Seq(Threat, Threat, Advantage)) mustEqual Seq(Threat)
      }
    }

  }
}
