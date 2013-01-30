package pparkkin.scala.akka.practice.view

import pparkkin.scala.akka.practice.model.{DebugInformation, GeneticMaterial}
import swing.Panel
import java.awt.{Polygon, Color, Graphics2D, Dimension}
import collection.immutable
import swing.event.{Key, KeyPressed}

class DisplayPanel(width: Int, height: Int, gi: GeneticMaterial, di: DebugInformation) extends Panel {

  preferredSize = new Dimension(width, height)
  focusable = true

  val DEBUG_INFO_COLOR = Color.YELLOW
  var debugInfoColor: Option[Color] = Some(DEBUG_INFO_COLOR)

  override
  def paintComponent(g: Graphics2D) {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, size.width, size.height)
    fillPolygons(g, gi.head.seq)
    debugInfoColor match {
      case Some(c) => {
        displayDebugInfo(g, c)
      }
      case None => {}
    }
  }

  def displayDebugInfo(g: Graphics2D, c: Color) {
    val oc = g.getColor
    g.setColor(c)
    val fm = g.getFontMetrics(g.getFont)
    val h = 2 + fm.getHeight
    var ch = h
    for (k <- di.keys()) {
      g.drawString(k+": "+di.get(k), 2, ch)
      ch += h
    }
    g.setColor(oc)
  }

  def fillPolygons(g: Graphics2D, data: immutable.IndexedSeq[Float]) {
    val oc = g.getColor
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
    g.setColor(oc)
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

  listenTo(this.keys)
  reactions += {
    case KeyPressed(_, Key.C, _, _) => {
      debugInfoColor = debugInfoColor match {
        case Some(_) => None
        case None => Some(DEBUG_INFO_COLOR)
      }
      repaint()
    }
  }

}
