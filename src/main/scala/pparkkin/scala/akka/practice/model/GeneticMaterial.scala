package pparkkin.scala.akka.practice.model

import collection.immutable
import util.Random
import java.awt.image.BufferedImage

class GeneticMaterial(val target: BufferedImage, private[this] var data: immutable.Seq[GeneticSequence]) {

  def head: GeneticSequence = data.head
  def push(s: GeneticSequence) { data = immutable.Vector(s) }

}

// The data in the genetic information is simply a list integers with
// length % 10 == 0. So n sets of 10 integers.
// Each set starts with four values representing a color: r, g, b, a
// Next three values are the x points, and the last three are the y points.
// Each set is so a coloured triangle in a 2D space.

object GeneticMaterial {
  val SET_SIZE = 10

  def random(img: BufferedImage): GeneticMaterial = {
    val range = math.max(img.getHeight, img.getWidth)
    val len = range - (range % GeneticMaterial.SET_SIZE)
    assert(len % SET_SIZE == 0)

    new GeneticMaterial(img, immutable.Vector(randomSequence(len)))
  }

  def randomSequence(len: Int): GeneticSequence = new GeneticSequence(1 to len map (_ => Random.nextFloat()))

}
