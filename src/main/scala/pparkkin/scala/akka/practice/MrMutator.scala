package pparkkin.scala.akka.practice

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

object MrMutator {
  
  def main(args : Array[String]) {
    val img =
      if (args.length > 0) {
        ImageIO.read(new File(args(0)))
      } else {
        null
      }
    val mutatorSystem = new MutatorSystem("MrMutator", img)
    mutatorSystem.run()
  }

}
