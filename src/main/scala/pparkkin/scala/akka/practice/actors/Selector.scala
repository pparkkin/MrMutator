package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import scala.math.abs
import collection.immutable
import java.awt.image.BufferedImage
import pparkkin.scala.akka.practice.model.GeneticMaterial

case class Select(one: immutable.IndexedSeq[Float])
//case class Selected(one: immutable.IndexedSeq[Float])

class Selector(gm: GeneticMaterial, displayer: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case Select(one) => {
      log.debug("Compare to best. If better, replace and inform display.")
      gm.push(one)
      displayer ! Display
      sender ! Mutate
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

  //  def distance(as: immutable.IndexedSeq[Float], bs: immutable.IndexedSeq[Float]): Float = {
  //    ( (as, bs).zipped map { (a: Float, b: Float) => abs(a - b) } ).sum
  //  }

}
