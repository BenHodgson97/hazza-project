package controllers

import actions.AuthAction
import actor.dice.DiceRollerActorProvider
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import com.google.inject.{Inject, Singleton}
import models.dice.Die._
import models.dice.{DiceUpdate, Die, Event, Symbol}
import models.request.AuthenticatedRequest
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import services.DiceCancellingService
import views.html.DiceRollerView
import repositories.DiceRollerRepository


@Singleton
class DiceRollerController @Inject()(
                                      val controllerComponents: ControllerComponents,
                                      authAction: AuthAction,
                                      diceRollerView: DiceRollerView,
                                      diceRollerRepository: DiceRollerRepository,
                                      diceRollerActorProvider: DiceRollerActorProvider,
                                      diceCancellingService: DiceCancellingService
                                    )(implicit mat: Materializer,
                                      actorSystem: ActorSystem) extends BaseController {
  def onPageLoad: Action[AnyContent] = authAction {implicit request: AuthenticatedRequest[AnyContent] =>

    val diceList: Seq[Die] = Seq(YellowProficiency, GreenAbility, RedChallenge, PurpleDifficulty)
    val result: Seq[Symbol] = diceList.flatMap(die => die.roll.symbols)

    val finalResult = diceCancellingService.cancelSymbols(result)

    Ok(diceRollerView(result, finalResult, diceRollerRepository.currentDiceState))
  }

  def diceRollerSocket: WebSocket = WebSocket.accept[Event, DiceUpdate] {
    _ => ActorFlow.actorRef{
      clientActor =>
        Props(diceRollerActorProvider.createActor(clientActor))
    }
  }
}
