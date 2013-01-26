package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}
import collection.immutable
import swing.Panel

case object Display

class Displayer(val panel: Panel) extends Actor with ActorLogging {

  def receive = {
    case Display =>
      log.debug("Command panel repaint.")
      panel.repaint()
    case msg =>
      log.error("Received unknown message: "+msg)
  }
}
