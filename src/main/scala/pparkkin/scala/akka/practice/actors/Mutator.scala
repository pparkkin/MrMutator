package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import collection.immutable
import util.Random
import collection.immutable.Stream.Empty
import pparkkin.scala.akka.practice.model.GeneticMaterial

case object Mutate
//case class Mutated(information: immutable.IndexedSeq[Float])

class Mutator(gm: GeneticMaterial, selector: ActorRef) extends Actor with ActorLogging {

  def receive =  {
    case Mutate => {
      log.debug("Get top genetic material, mutate, and send to Selector")
      val m: immutable.IndexedSeq[Float] = gm.head
      selector ! Select(GeneticMaterial.randomSequence(m.length))
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

//  def mutate: immutable.IndexedSeq[Float] => immutable.IndexedSeq[Float] = {
//    case IndexedSeq() => immutable.IndexedSeq[Float]()
//    case seq => {
//      val n = Random.nextInt(seq.length)
//      val k = Random.nextInt() % seq.length
//      val left = seq.take(n)
//      val elem = seq(n)+k
//      val right = seq.takeRight(seq.length-(n+1))
//      (left :+ elem) ++ right
//    }
//  }

}
