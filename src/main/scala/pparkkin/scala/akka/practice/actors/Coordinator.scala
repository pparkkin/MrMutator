package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import collection.immutable

case class BeginMutating()

class Coordinator(mutators: ActorRef, selector: ActorRef, displayer: ActorRef, initialSeed: Int) extends Actor with ActorLogging {

  var currentGen: immutable.List[immutable.IndexedSeq[Int]] = Nil

  def insertGeneticInformation(cur: immutable.List[immutable.IndexedSeq[Int]], inf: immutable.IndexedSeq[Int]): immutable.List[immutable.IndexedSeq[Int]] = {
    if (cur.size < initialSeed) {
      inf :: cur
    } else {
      inf :: cur.take(cur.size-1)
    }
  }

  def beginMutating() = {
    // Seed current gen with genetic information
    1 to initialSeed foreach {
      i: Int => currentGen = insertGeneticInformation(currentGen, immutable.Vector(1, 2, 3))
    }

    // Start mutating!
    currentGen foreach {
      genes => mutators ! Mutate(genes)
    }
  }

  def receive = {
    case BeginMutating =>
      beginMutating
    case Mutated(i) =>
      selector ! Select(i, currentGen.head)
    case Selected(i) =>
      displayer ! Display(i)
      currentGen = insertGeneticInformation(currentGen, i)
      mutators ! Mutate(currentGen.head)
    case msg =>
      log.error("Received unknown message: "+msg)
  }

}
