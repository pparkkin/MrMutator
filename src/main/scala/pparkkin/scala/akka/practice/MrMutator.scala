package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import swing._
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Dimension}
import scala.Some
import javax.swing.Box

object MrMutator extends SimpleSwingApplication {

  override
  def main(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {
        super.main(args)
//        secondFrame.visible = true
        setImage(img)
//        val mutatorSystem = new MutatorSystem("MrMutator", img, p)
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
    val secondFrame = currentFrame
    secondFrame.visible = true
  }

  def currentFrame = new Frame {
    title = "MrMutator - Current"
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += currentPanel
    }
  }

  def setImage(img: BufferedImage) {
    targetPanel.setImage(img)
    currentPanel.setImage(img)
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

  val currentPanel = new Panel {
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

}


