package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import scala.math.abs
import collection.immutable
import java.awt.image.BufferedImage
import pparkkin.scala.akka.practice.model.{GeneticSequence, GeneticMaterial}

case class Select(one: GeneticSequence)
//case class Selected(one: immutable.IndexedSeq[Float])

class Selector(gm: GeneticMaterial, displayer: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case Select(one) => {
      // Tell mutator to start working on next mutation.
      sender ! Mutate

      // Compare new mutation. If better, replace and display.
      if (one > gm.head) {
        gm.push(one)
        displayer ! Display
      }
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

  //  def distance(as: immutable.IndexedSeq[Float], bs: immutable.IndexedSeq[Float]): Float = {
  //    ( (as, bs).zipped map { (a: Float, b: Float) => abs(a - b) } ).sum
  //  }

}
