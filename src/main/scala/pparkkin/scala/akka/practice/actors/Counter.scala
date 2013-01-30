package pparkkin.scala.akka.practice.actors

import pparkkin.scala.akka.practice.model.DebugInformation
import akka.actor.Actor

case object Increment

class Counter(di: DebugInformation, cn: String) extends Actor {

  def receive = {
    case Increment => di.increment(cn)
  }

}
