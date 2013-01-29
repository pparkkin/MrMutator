package pparkkin.scala.akka.practice.actors

import akka.actor.{ActorRef, ActorLogging, Actor}
import java.awt.image.{Raster, BufferedImage}
import pparkkin.scala.akka.practice.model.{GeneticSequence, GeneticMaterial}
import pparkkin.scala.akka.practice.view.ImageBuilder
import concurrent.{Await, ExecutionContext, Future, future}
import concurrent.duration._
import ExecutionContext.Implicits.global
import util.Success

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
    val ff: Future[Float] = future {
      distance(f, tg)
    }

    val sf: Future[Float] = future {
      distance(s, tg)
    }

    val r = for { a <- ff
                  b <- sf } yield b - a

    Await.result(r, 5.seconds)
  }

  def distance(gs: GeneticSequence, tg: BufferedImage): Float = {
    gs.distance match {
      case Some(d) => d
      case None => {
        val img = imageBuilder.buildImage(gs)
        val d = distance(img.getData, tg.getData)
        gs.distance = Some(d)
        d
      }
    }
  }

  def distance(f: Raster, s: Raster): Float = {
    var sum = 0f
    var count = 0

    for (j <- 0 until f.getHeight) {
      for (i <- 0 until s.getWidth) {
        sum += distanceRGB(f.getPixel(i, j, null), s.getPixel(i, j, null))
        count += 1
      }
    }

    if (count == 0) {
      0
    } else {
      sum / count
    }
  }

  def distanceRGB(f: Array[Int], s: Array[Int]): Float = {
    (f, s).zipped.map((a,b) => { math.abs(a - b)}).sum

  }

}
