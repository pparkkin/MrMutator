package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import collection.immutable
import util.Random
import collection.immutable.Stream.Empty
import pparkkin.scala.akka.practice.model.{GeneticSequence, GeneticMaterial}

case object Mutate
//case class Mutated(information: immutable.IndexedSeq[Float])

class Mutator(gm: GeneticMaterial, selector: ActorRef, counter: ActorRef) extends Actor with ActorLogging {

  def receive =  {
    case Mutate => {
      val m = gm.head
      val seq = mutate(m.seq)
      selector ! Select(new GeneticSequence(seq))
      counter ! Increment
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

  def mutate(seq: immutable.IndexedSeq[Float]): immutable.IndexedSeq[Float] = {
    val p = seq.length/gm.MAX_SIZE.toFloat
    val r = Random.nextFloat()

    if (r < p) mutatePoint(seq)
    else addSet(seq)
  }

  def addSet(seq: immutable.IndexedSeq[Float]): immutable.IndexedSeq[Float] = {
    seq ++ (0 until GeneticMaterial.SET_SIZE map { _ => Random.nextFloat() })

  }

  def mutatePoint(seq: immutable.IndexedSeq[Float]): immutable.IndexedSeq[Float] = {
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
