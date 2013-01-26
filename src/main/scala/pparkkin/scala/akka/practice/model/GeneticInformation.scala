package pparkkin.scala.akka.practice.model

import collection.immutable
import util.Random

class GeneticInformation(val data: immutable.IndexedSeq[Float])

// The data in the genetic information is simply a list integers with
// length % 10 == 0. So n sets of 10 integers.
// Each set starts with four values representing a color: r, g, b, a
// Next three values are the x points, and the last three are the y points.
// Each set is so a coloured triangle in a 2D space.

object GeneticInformation {
  val SET_SIZE = 10

  def random(len: Int): GeneticInformation = {
    assert(len % SET_SIZE == 0)

    new GeneticInformation(1 to len map (_ => Random.nextFloat()))
  }
}
