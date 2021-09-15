package services

import models.dice.Symbol
import models.dice.Symbol._

class DiceCancellingService {
  def cancelSymbols(result: Seq[Symbol]): Seq[Symbol] = {
    val symbolCount: Map[Symbol, Int] = result.groupBy(identity).view.mapValues(_.size).toMap.withDefaultValue(0)
    val successFailure: Seq[Symbol] = {
      val successes: Int = symbolCount(Success) + symbolCount(Triumph)
      val failures: Int = symbolCount(Failure) + symbolCount(Despair)
      val successFailureCount: Int = successes - failures
      if(successFailureCount > 0){
        Seq.fill(successFailureCount)(Success)
      } else {
        Seq.fill(successFailureCount.abs)(Failure)
      }
    }

    val advantageThreat: Seq[Symbol] = {
      val advantages: Int = symbolCount(Advantage)
      val threats: Int = symbolCount(Threat)
      val advantageThreatCount: Int = advantages - threats
      if(advantageThreatCount > 0){
        Seq.fill(advantageThreatCount)(Advantage)
      } else {
        Seq.fill(advantageThreatCount.abs)(Threat)
      }
    }

    val triumph: Seq[Symbol] = Seq.fill(symbolCount(Triumph))(Triumph)

    val despair: Seq[Symbol] = Seq.fill(symbolCount(Despair))(Despair)
    successFailure ++ advantageThreat ++ triumph ++ despair
  }
}
