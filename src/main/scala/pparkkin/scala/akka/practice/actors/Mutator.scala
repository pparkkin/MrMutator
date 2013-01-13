package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorLogging, Actor}
import collection.immutable
import util.Random
import collection.immutable.Stream.Empty

case class Mutate(information: immutable.IndexedSeq[Int])
case class Mutated(information: immutable.IndexedSeq[Int])

class Mutator extends Actor with ActorLogging {
  def receive =  {
    case Mutate(seq) => {
      log.debug("Received Mutate message.")
      sender ! Mutated(mutate(seq))
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

  def mutate: immutable.IndexedSeq[Int] => immutable.IndexedSeq[Int] = {
    case IndexedSeq() => immutable.IndexedSeq[Int]()
    case seq => {
      val n = Random.nextInt(seq.length)
      val k = Random.nextInt() % seq.length
      val left = seq.take(n)
      val elem = seq(n)+k
      val right = seq.takeRight(seq.length-(n+1))
      (left :+ elem) ++ right
    }
  }

}
