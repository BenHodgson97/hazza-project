package models.dice

sealed trait Symbol {
  val opposite: Symbol
}

object Symbol {
  case object Success extends Symbol {
    override val opposite: Symbol = Failure
  }

  case object Advantage extends Symbol {
    override val opposite: Symbol = Threat
  }

  case object Triumph extends Symbol {
    override val opposite: Symbol = Failure
  }

  case object Failure extends Symbol {
    override val opposite: Symbol = Success
  }

  case object Threat extends Symbol {
    override val opposite: Symbol = Advantage
  }

  case object Despair extends Symbol {
    override val opposite: Symbol = Success
  }
}
