package models.dice

import models.dice.DiceFace._

import scala.util.Random

sealed trait Die {
  def roll: DiceFace = faces(Random.nextInt(faces.size))
  val faces: Seq[DiceFace]
  val colour: String
}

object Die {

  case object GreenAbility extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, DoubleAdvantage, SingleSuccess, SingleAdvantage, DoubleSuccess, SuccessAdvantage, SingleAdvantage, SingleSuccess)
    val colour = "green"
  }
  case object YellowProficiency extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, DoubleSuccess, DoubleSuccess, SuccessAdvantage, SuccessAdvantage, SuccessAdvantage, SingleSuccess, SingleSuccess, DoubleAdvantage, DoubleAdvantage, SingleAdvantage, Triumph)
    val colour = "yellow"
  }
  case object BlueBoost extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, Blank, SingleSuccess, SingleAdvantage, DoubleAdvantage, SuccessAdvantage)
    val colour = "blue"
  }
  case object PurpleDifficulty extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, SingleThreat, SingleThreat, SingleThreat, DoubleThreat, SingleFailure, DoubleFailure, FailureThreat)
    val colour = "purple"
  }
  case object RedChallenge extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, DoubleFailure, DoubleFailure, FailureThreat, FailureThreat, DoubleThreat, DoubleThreat, SingleFailure, SingleFailure, SingleThreat, SingleThreat, Despair)
    val colour = "red"
  }
  case object BlackSetback extends Die {
    val faces: Seq[DiceFace] = Seq(Blank, Blank, SingleThreat, SingleThreat, SingleFailure, SingleFailure)
    val colour = "black"
  }
}
