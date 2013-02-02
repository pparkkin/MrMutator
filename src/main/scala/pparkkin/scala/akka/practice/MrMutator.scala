package pparkkin.scala.akka.practice

import javax.imageio.ImageIO
import java.io.File
import model.{DebugInformation, GeneticMaterial}
import swing._
import java.awt.image.BufferedImage
import scala.Some
import view.{ImagePanel, DisplayPanel}

object MrMutator extends SwingApplication {

  def startup(args : Array[String]) {
    readImage(args) match {
      case Some(img) => {

        val tp = new ImagePanel(img)
        val mf = new MrMutatorFrame(tp)
        mf.visible = true

        // Init genetic material
        val gi = GeneticMaterial.empty(img)

        // Debug information
        val di = new DebugInformation

        // Set up current status image panel
        val panel = new DisplayPanel(img.getWidth, img.getHeight, gi, di)

        // Attach current status image panel to frame and visible
        val curF = new DisplayFrame(panel)
        // Place next to the target image panel
        val b = mf.bounds
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

  class MrMutatorFrame(panel: Panel) extends Frame {
    title = "MrMutator - Target"
    contents = panel
  }

  class DisplayFrame(panel: Panel) extends Frame {
    title = "MrMutator - Current"
    contents = panel
  }

}


