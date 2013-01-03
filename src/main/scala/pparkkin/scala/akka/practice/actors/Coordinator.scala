package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}

case class BeginMutating

class Coordinator(mutators: ActorRef, selector: ActorRef, displayer: ActorRef, initialSeed: Int) extends Actor with ActorLogging {

  var currentGen: List[List[Int]] = Nil

  def insertGeneticInformation(cur: List[List[Int]], inf: List[Int]): List[List[Int]] = {
    if (cur.size < initialSeed) {
      inf :: cur
    } else {
      inf :: cur.take(initialSeed-1)
    }
  }

  def beginMutating() = {
    // Seed current gen with genetic information
    1 to initialSeed foreach {
      i => currentGen = insertGeneticInformation(currentGen, List(1, 2, 3))
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
      selector ! Select(i, currentGen head)
    case Selected(i) =>
      displayer ! Display(i)
      currentGen = insertGeneticInformation(currentGen, i)
      mutators ! Mutate(currentGen head)
    case msg =>
      log.error("Received unknown message: "+msg)
  }

}
