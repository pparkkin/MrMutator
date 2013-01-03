package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}

case class Display(information: List[Int])

class Displayer extends Actor with ActorLogging {

  def receive = {
    case Display(i) =>
      log.info("Got: "+i)
    case msg =>
      log.error("Received unknown message: "+msg)
  }
}
