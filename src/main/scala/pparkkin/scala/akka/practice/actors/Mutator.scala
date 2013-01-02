package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}

case class Mutate(information: Seq[Int])

class Mutator extends Actor with ActorLogging {
  def receive =  {
    case Mutate(data) => {
      log.info("Received message 'Mutate' with data: "+data)
    }
  }
}
