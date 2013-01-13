package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}
import scala.math.abs
import collection.immutable

case class Select(one: immutable.IndexedSeq[Int], two: immutable.IndexedSeq[Int])
case class Selected(information: immutable.IndexedSeq[Int])

class Selector(target: immutable.IndexedSeq[Int]) extends Actor with ActorLogging {

  def distance(as: immutable.IndexedSeq[Int], bs: immutable.IndexedSeq[Int]): Int = {
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
