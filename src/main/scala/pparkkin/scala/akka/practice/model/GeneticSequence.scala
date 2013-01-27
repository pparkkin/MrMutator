package pparkkin.scala.akka.practice.model

import collection.immutable
import java.awt.image.BufferedImage

class GeneticSequence(val seq: immutable.IndexedSeq[Float], val img: Option[BufferedImage] = None) extends Ordered[GeneticSequence] {
  def compare(that: GeneticSequence): Int = {
    1
  }

  def length = seq.length

}
