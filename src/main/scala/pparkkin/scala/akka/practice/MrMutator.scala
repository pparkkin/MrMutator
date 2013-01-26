package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import model.GeneticMaterial
import swing._
import java.awt.image.BufferedImage
import java.awt.{Polygon, Color, Graphics2D, Dimension}
import scala.Some
import javax.swing.Box
import collection.immutable
import view.DisplayPanel

object MrMutator extends SimpleSwingApplication {

  override
  def main(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {
        super.main(args)
        // Set up target image panel
        targetPanel.setImage(img)

        // Init genetic material
        val gi = GeneticMaterial.random(img)

        // Set up current status image panel
        val panel = new DisplayPanel(img.getWidth, img.getHeight, gi)

        // Attach current status image panel to frame and visible
        val curF = currentFrame(panel)
        curF.visible = true

        // Create actor system
        val mutatorSystem = new MutatorSystem("MrMutator", gi, panel)

        // Run it
        mutatorSystem.run()
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

}


