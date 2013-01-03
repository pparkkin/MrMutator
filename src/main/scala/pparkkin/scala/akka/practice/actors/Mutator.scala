package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}

case class Mutate(information: List[Int])
case class Mutated(information: List[Int])

class Mutator extends Actor with ActorLogging {
  def receive =  {
    case Mutate(fst :: rest) => {
      log.debug("Received Mutate message.")
      sender ! Mutated((fst + 1) :: rest)
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }
}
