package controllers

import actions.AuthAction
import com.google.inject.{Inject, Singleton}
import models.dice.{Die, Symbol}
import models.dice.Die._
import models.request.AuthenticatedRequest
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.DiceCancellingService
import views.html.DiceRollerView

@Singleton
class DiceRollerController @Inject()(
                                      val controllerComponents: ControllerComponents,
                                      authAction: AuthAction,
                                      diceRollerView: DiceRollerView,
                                      diceCancellingService: DiceCancellingService
                                    ) extends BaseController {
    def onPageLoad: Action[AnyContent] = authAction {implicit request: AuthenticatedRequest[AnyContent] =>

      val diceList: Seq[Die] = Seq(YellowProficiency, GreenAbility, RedChallenge, PurpleDifficulty)
      val result: Seq[Symbol] = diceList.flatMap(die => die.roll.symbols)

      val finalResult = diceCancellingService.cancelSymbols(result)

      Ok(diceRollerView(result, finalResult))
    }
}
