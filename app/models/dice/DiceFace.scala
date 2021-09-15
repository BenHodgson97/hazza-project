package models.dice

import models.dice.Symbol._

sealed class DiceFace(a: Option[Symbol] = None, b: Option[Symbol] = None) {
  def symbols: Seq[Symbol] = Seq(a, b).flatten
}

object DiceFace {
  case object Blank extends DiceFace()

  case object SingleSuccess extends DiceFace(Some(Success))
  case object DoubleSuccess extends DiceFace(Some(Success), Some(Success))
  case object SingleAdvantage extends DiceFace(Some(Advantage))
  case object DoubleAdvantage extends DiceFace(Some(Advantage), Some(Advantage))
  case object SuccessAdvantage extends DiceFace(Some(Success), Some(Advantage))
  case object Triumph extends DiceFace(Some(Symbol.Triumph))

  case object SingleFailure extends DiceFace(Some(Failure))
  case object DoubleFailure extends DiceFace(Some(Failure), Some(Failure))
  case object SingleThreat extends DiceFace(Some(Threat))
  case object DoubleThreat extends DiceFace(Some(Threat), Some(Threat))
  case object FailureThreat extends DiceFace(Some(Failure), Some(Threat))
  case object Despair extends DiceFace(Some(Symbol.Despair))
}
