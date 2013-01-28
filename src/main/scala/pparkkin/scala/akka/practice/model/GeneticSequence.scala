package pparkkin.scala.akka.practice.model

import collection.immutable
import java.awt.image.BufferedImage

class GeneticSequence(val seq: immutable.IndexedSeq[Float], var distance: Option[Float] = None) extends Ordered[GeneticSequence] {
  def compare(that: GeneticSequence): Int = {
    1
  }

  def length = seq.length

}
