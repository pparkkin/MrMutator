package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import model.GeneticInformation
import swing._
import java.awt.image.BufferedImage
import java.awt.{Polygon, Color, Graphics2D, Dimension}
import scala.Some
import javax.swing.Box
import collection.immutable

object MrMutator extends SimpleSwingApplication {

  override
  def main(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {
        super.main(args)
        targetPanel.setImage(img)
        val range = math.max(img.getHeight, img.getWidth)
        val gi = GeneticInformation.random(range - (range % GeneticInformation.SET_SIZE))
        val panel = currentPanel(img.getWidth, img.getHeight, gi)
        val curF = currentFrame(panel)
        curF.visible = true
//        val mutatorSystem = new MutatorSystem("MrMutator", img, gi, panel)
//        mutatorSystem.run()
      }
      case None => {}
    }
  }

  def readImage(args: Array[String]): Option[BufferedImage] = {
    if (args.length > 0) {
      Some(ImageIO.read(new File(args(0))))
    } else {
      None
    }
  }

  def top = new MainFrame {
    title = "MrMutator - Target"
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += targetPanel
    }
  }

  def currentFrame(panel: Panel) = new Frame {
    title = "MrMutator - Current"
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += panel
    }
  }

  val targetPanel = new Panel {
    var img: Option[BufferedImage] = None

    def setImage(img: BufferedImage) {
      preferredSize = new Dimension(img.getWidth, img.getHeight)
      this.img = Some(img)
      repaint()
    }

    override
    def paintComponent(g: Graphics2D) {
      this.img match {
        case Some(i) =>
          g.drawImage(i, 0, 0, null)
        case None => None
      }
    }
  }

  def currentPanel(width: Int, height: Int, gi: GeneticInformation) = new Panel {
    preferredSize = new Dimension(width, height)

    override
    def paintComponent(g: Graphics2D) {
      g.setColor(Color.BLACK)
      g.fillRect(0, 0, size.width, size.height)
      fillPolygons(g, gi.data)
    }

    def fillPolygons(g: Graphics2D, data: immutable.IndexedSeq[Int]) {
      if (data.length < GeneticInformation.SET_SIZE) return

      val c = constructColor(data)
      val p = constructPolygon(data.drop(4))

      (c, p) match {
        case (Some(color), Some(polygon)) => {
          g.setColor(color)
          g.fillPolygon(polygon)
          //println("Polygon")
        }
      }

      fillPolygons(g, data.drop(GeneticInformation.SET_SIZE))
    }

    def constructColor(data: immutable.IndexedSeq[Int]): Option[Color] = {
      if (data.length < 4) None

      val r = data(0)
      val g = data(1)
      val b = data(2)
      val a = data(3)

      print(r, g, b, a)

      Some(new Color(r, g, b, a))
    }

    def constructPolygon(data: immutable.IndexedSeq[Int]): Option[Polygon] = {
      if (data.length < 6) None

      val xs = data.take(3)
      val ys = data.drop(3).take(3)

      print(" -> ")
      println(xs, ys)

      Some(new Polygon(xs.toArray, ys.toArray, xs.length))
    }
  }

}


