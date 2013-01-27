package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import scala.math.abs
import collection.immutable
import java.awt.image.BufferedImage
import pparkkin.scala.akka.practice.model.{GeneticSequence, GeneticMaterial}
import pparkkin.scala.akka.practice.view.ImageBuilder

case class Select(one: GeneticSequence)
//case class Selected(one: immutable.IndexedSeq[Float])

class Selector(gm: GeneticMaterial, displayer: ActorRef) extends Actor with ActorLogging {
  val tg = gm.target
  val imageBuilder = new ImageBuilder(tg.getWidth, tg.getHeight, tg.getType)

  def receive = {
    case Select(one) => {
      // Tell mutator to start working on next mutation.
      sender ! Mutate

      // Compare new mutation. If better, replace and display.
      if (compare(one, gm.head) > 0) {
        gm.push(one)
        displayer ! Display
      }
    }
    case msg => {
      log.error("Received unknown message: "+msg)
    }
  }

  def compare(f: GeneticSequence, s: GeneticSequence): Float = {
    val fi = imageBuilder.buildImage(f)
    val si = imageBuilder.buildImage(s)

    val a = distance(fi, tg)
    val b = distance(si, tg)

    b - a

  }

  def distance(fi: BufferedImage, si: BufferedImage): Float = {
    var sum = 0f
    var count = 0

    for (j <- 0 until fi.getHeight) {
      for (i <- 0 until si.getWidth) {
        sum += distanceRGB(fi.getRGB(i, j), si.getRGB(i, j))
        count += 1
      }
    }

    if (count == 0) {
      0
    } else {
      sum / count
    }
  }

  def distanceRGB(f: Int, s: Int): Float = {
    val (fr, fg, fb, _) = splitRGB(f)
    val (sr, sg, sb, _) = splitRGB(s)

    (math.abs(fr-sr) + math.abs(fg-sg) + math.abs(fb-sb))/3f

  }

  def splitRGB(r: Int): (Int, Int, Int, Int) = {
    ((r >> 24) & 0xF,
     (r >> 16) & 0xF,
     (r >> 8) & 0xF,
     r & 0xF)
  }

  //  def distance(as: immutable.IndexedSeq[Float], bs: immutable.IndexedSeq[Float]): Float = {
  //    ( (as, bs).zipped map { (a: Float, b: Float) => abs(a - b) } ).sum
  //  }

}
