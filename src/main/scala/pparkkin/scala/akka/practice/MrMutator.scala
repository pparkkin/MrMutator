package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import model.GeneticInformation
import swing._
import java.awt.image.BufferedImage
import java.awt.{Color, Graphics2D, Dimension}
import scala.Some
import javax.swing.Box

object MrMutator extends SimpleSwingApplication {

  override
  def main(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {
        super.main(args)
        targetPanel.setImage(img)
        val range = math.max(img.getHeight, img.getWidth)
        val gi = GeneticInformation.random(range - (range % 7))
        val curF = currentFrame(currentPanel(img.getWidth, img.getHeight, gi))
        curF.visible = true
//        val mutatorSystem = new MutatorSystem("MrMutator", img, gi)
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
    }
  }

}


