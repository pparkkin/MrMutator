package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import scala.swing._
import java.awt.image.BufferedImage
import javax.swing.JPanel

object MrMutator extends SimpleSwingApplication {

  override
  def main(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {
        val mutatorSystem = new MutatorSystem("MrMutator", img, p)
        mutatorSystem.run()
        super.main(args)
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
    title = "MrMutator"
    contents = p
  }

  val p = new DrawingPanel {
    preferredSize = new Dimension(200, 200)
  }

}

class DrawingPanel extends Panel {
  override lazy val peer: JPanel = new JPanel with SuperMixin
  def getGraphics = peer.getGraphics
}

