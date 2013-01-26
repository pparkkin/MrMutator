package pparkkin.scala.akka.practice.view

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Dimension}
import swing.Panel

class ImagePanel extends Panel {
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
