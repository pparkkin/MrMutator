package pparkkin.scala.akka.practice.view

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Dimension}
import swing.Panel

class ImagePanel(val img: BufferedImage) extends Panel {

  preferredSize = new Dimension(img.getWidth, img.getHeight)

  override
  def paintComponent(g: Graphics2D) {
    g.drawImage(img, 0, 0, null)
  }

}
