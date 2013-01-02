package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}

case class Select(one: Seq[Int], two: Seq[Int])
case class Selected(information: Seq[Int])

class Selector extends Actor with ActorLogging {
  def receive = {
    case Select(one, _) =>
      log.debug("Received Select message.")
      sender ! Selected(one)
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

}
