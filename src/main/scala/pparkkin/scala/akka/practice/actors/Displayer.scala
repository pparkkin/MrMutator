package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}
import collection.immutable
import java.awt.{Color, Graphics}
import pparkkin.scala.akka.practice.DrawingPanel

case class Display(information: immutable.IndexedSeq[Int])

class Displayer(val panel: DrawingPanel) extends Actor with ActorLogging {

  def receive = {
    case Display(i) =>
      log.info("Got: Display("+i+")")
      val graphics = panel.getGraphics
      if (graphics != null) {
        graphics.setColor(new Color(0))
        graphics.fillRect(i(0), i(1), i(2), i(2))
//        panel.repaint()
      } else {
        log.error("Null graphics object.")
      }
    case msg =>
      log.error("Received unknown message: "+msg)
  }
}
