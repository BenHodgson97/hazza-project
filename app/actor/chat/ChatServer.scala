package actor.chat

import akka.actor.Actor

trait ChatServer extends Actor {
  def receive: Receive = sessionManagement orElse chatManagement

  protected def chatManagement: Receive
  protected def sessionManagement: Receive
}
