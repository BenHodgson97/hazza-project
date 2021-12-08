package models.character

import models.character.Characteristic._

sealed trait House {
  val primary: Characteristic
  val secondary: Characteristic
}

object House {
  case class Gryffindor(secondary: Characteristic) extends House {
    override val primary: Characteristic = Willpower
  }

  case class Hufflepuff(secondary: Characteristic) extends House {
    override val primary: Characteristic = Finesse
  }

  case class Ravenclaw(secondary: Characteristic) extends House {
    override val primary: Characteristic = Intellect
  }

  case class FerocitySlytherin(secondary: Characteristic) extends House {
    override val primary: Characteristic = Ferocity
  }

  case class CunningSlytherin(secondary: Characteristic) extends House {
    override val primary: Characteristic = Cunning
  }
}
