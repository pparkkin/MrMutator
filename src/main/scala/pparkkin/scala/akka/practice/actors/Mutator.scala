package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import collection.immutable
import util.Random
import collection.immutable.Stream.Empty
import pparkkin.scala.akka.practice.model.{GeneticSequence, GeneticMaterial}

case object Mutate
//case class Mutated(information: immutable.IndexedSeq[Float])

class Mutator(gm: GeneticMaterial, selector: ActorRef) extends Actor with ActorLogging {

  def receive =  {
    case Mutate => {
      val m = gm.head
      val seq = mutate(m.seq)
      selector ! Select(new GeneticSequence(seq))
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

  def mutate: immutable.IndexedSeq[Float] => immutable.IndexedSeq[Float] = {
    case immutable.IndexedSeq() => immutable.IndexedSeq[Float]()
    case seq => {
      val n = Random.nextInt(seq.length)
      val k = Random.nextFloat()
      val left = seq.take(n)
      val elem = if (Random.nextBoolean()) {
        val t = seq(n)+k
        if (t > 1) 1 else t
      } else {
        val t = seq(n)-k
        if (t < 0) 0 else t
      }
      val right = seq.takeRight(seq.length-(n+1))
      (left :+ elem) ++ right
    }
  }

}
