package pparkkin.scala.akka.practice.model

import collection.immutable
import util.Random

class GeneticInformation(data: immutable.IndexedSeq[Int])

object GeneticInformation {
  def random(len: Int): GeneticInformation = {
    assert(len % 7 == 0)

    new GeneticInformation(1 to len map (_ => Random.nextInt(256)))
  }
}
