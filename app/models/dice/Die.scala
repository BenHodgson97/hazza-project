package models.dice

import models.dice.DiceFace._

import scala.util.Random

sealed trait Die {
  def roll: DiceFace = faces(Random.nextInt(faces.size))
  val faces: Seq[DiceFace]
}

object Die {

  case object GreenAbility extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, DoubleAdvantage, SingleSuccess, SingleAdvantage, DoubleSuccess, SuccessAdvantage, SingleAdvantage, SingleSuccess)
  }
  case object YellowProficiency extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, DoubleSuccess, DoubleSuccess, SuccessAdvantage, SuccessAdvantage, SuccessAdvantage, SingleSuccess, SingleSuccess, DoubleAdvantage, DoubleAdvantage, SingleAdvantage, Triumph)
  }
  case object BlueBoost extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, Blank, SingleSuccess, SingleAdvantage, DoubleAdvantage, SuccessAdvantage)
  }
  case object PurpleDifficulty extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, SingleThreat, SingleThreat, SingleThreat, DoubleThreat, SingleFailure, DoubleFailure, FailureThreat)
  }
  case object RedChallenge extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, DoubleFailure, DoubleFailure, FailureThreat, FailureThreat, DoubleThreat, DoubleThreat, SingleFailure, SingleFailure, SingleThreat, SingleThreat, Despair)
  }
  case object BlackSetback extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, Blank, SingleThreat, SingleThreat, SingleFailure, SingleFailure)
  }
}
