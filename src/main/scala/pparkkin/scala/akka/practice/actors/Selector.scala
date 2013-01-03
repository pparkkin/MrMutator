package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}
import scala.math.abs

case class Select(one: List[Int], two: List[Int])
case class Selected(information: List[Int])

class Selector(target: List[Int]) extends Actor with ActorLogging {

  def distance(as: List[Int], bs: List[Int]): Int = {
    ( (as, bs).zipped map ((a: Int, b: Int) => abs(a - b)) ).sum
  }

  def receive = {
    case Select(one, two) =>
      log.debug("Received Select message.")
      if ( distance(two, target) < distance(one, target) ) {
        sender ! Selected(two)
      } else {
        sender ! Selected(one)
      }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

}
