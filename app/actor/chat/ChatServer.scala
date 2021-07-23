package actor.chat

import akka.actor.Actor

trait ChatServer extends Actor {
  def receive: Receive = sessionManagement orElse chatManagement orElse keepAlive

  protected def chatManagement: Receive
  protected def sessionManagement: Receive
  protected def keepAlive: Receive
}
