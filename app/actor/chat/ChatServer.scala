package actor.chat

import akka.actor.Actor

trait ChatServer extends Actor {

  // actor message handler
  def receive: Receive = sessionManagement orElse chatManagement

  // abstract methods to be defined somewhere else
  protected def chatManagement: Receive
  protected def sessionManagement: Receive

}


