package pparkkin.scala.akka.practice.view

import pparkkin.scala.akka.practice.model.{GeneticMaterial, GeneticSequence}
import java.awt.image.BufferedImage
import java.awt.{Polygon, Color, Graphics2D}
import collection.immutable

class ImageBuilder(val width: Int, val height: Int, val imgType: Int) {

  def buildImage(gs: GeneticSequence): BufferedImage = {
    val img = new BufferedImage(width, height, imgType)
    val g = img.createGraphics

    fillPolygons(g, gs.seq)

    img

  }

  def fillPolygons(g: Graphics2D, data: immutable.IndexedSeq[Float]) {
    if (data.length < GeneticMaterial.SET_SIZE) return

    val c = constructColor(data)
    val p = constructPolygon(data.drop(4))

    (c, p) match {
      case (Some(color), Some(polygon)) => {
        g.setColor(color)
        g.fillPolygon(polygon)
        //println("Polygon")
      }
    }

    fillPolygons(g, data.drop(GeneticMaterial.SET_SIZE))
  }

  def constructColor(data: immutable.IndexedSeq[Float]): Option[Color] = {
    if (data.length < 4) None

    val r = data(0)
    val g = data(1)
    val b = data(2)
    val a = data(3)

    //      print(r, g, b, a)

    Some(new Color(r, g, b, a))
  }

  def constructPolygon(data: immutable.IndexedSeq[Float]): Option[Polygon] = {
    if (data.length < 6) None

    val xs = data.take(3).map { x => (x * width).toInt }
    val ys = data.drop(3).take(3).map { y => (y * height).toInt }

    //      print(" -> ")
    //      println(xs, ys)

    Some(new Polygon(xs.toArray, ys.toArray, xs.length))
  }

}
