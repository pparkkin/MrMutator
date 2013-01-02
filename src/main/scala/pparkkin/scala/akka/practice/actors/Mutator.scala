package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}

case class Mutate(information: Seq[Int])
case class Mutated(information: Seq[Int])

class Mutator extends Actor with ActorLogging {
  def receive =  {
    case Mutate(fst :: rest) => {
      log.info("Received message 'Mutate' with data: "+(fst :: rest))
      sender ! Mutated((fst + 1) :: rest)
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }
}
