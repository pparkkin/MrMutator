package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}
import collection.immutable
import swing.Panel

case class Display(information: immutable.IndexedSeq[Int])

class Displayer(val panel: Panel) extends Actor with ActorLogging {

  def receive = {
    case Display(i) =>
      log.info("Got: Display("+i+")")
      panel.repaint()
    case msg =>
      log.error("Received unknown message: "+msg)
  }
}
