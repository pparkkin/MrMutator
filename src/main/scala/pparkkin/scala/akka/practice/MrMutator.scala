package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import model.{DebugInformation, GeneticMaterial}
import swing._
import java.awt.image.BufferedImage
import java.awt.{Polygon, Color, Graphics2D, Dimension}
import scala.Some
import javax.swing.Box
import collection.immutable
import view.{ImagePanel, DisplayPanel}

object MrMutator extends SimpleSwingApplication {

  override
  def main(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {
        super.main(args)
        // Set up target image panel
        targetPanel.setImage(img)

        // Init genetic material
        val gi = GeneticMaterial.empty(img)

        // Debug information
        val di = new DebugInformation

        // Set up current status image panel
        val panel = new DisplayPanel(img.getWidth, img.getHeight, gi, di)

        // Attach current status image panel to frame and visible
        val curF = new DisplayFrame(panel)
        // Place next to the target image panel
        val b = mainFrame.bounds
        curF.bounds = new Rectangle((b.x+b.width), b.y, b.width, b.height)
        curF.visible = true

        // Create actor system
        val mutatorSystem = new MutatorSystem("MrMutator", gi, panel, di)

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

  def top = mainFrame

  /* In order to conform to Swing's threading policy, never implement top or
     any additional member that created Swing components as a value unless
     component creation happens on the EDT (see Swing.onEDT and
     Swing.onEDTWait). Lazy values are okay for the same reason if they are
     initialized on the EDT always.
     http://www.scala-lang.org/api/current/index.html#scala.swing.SimpleSwingApplication
    */
  lazy val mainFrame = new MainFrame {
    title = "MrMutator - Target"
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += targetPanel
    }
  }

  class DisplayFrame(panel: Panel) extends Frame {
    title = "MrMutator - Current"
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += panel
    }
  }

  val targetPanel = new ImagePanel

}


