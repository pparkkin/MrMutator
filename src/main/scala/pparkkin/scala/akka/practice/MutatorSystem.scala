package pparkkin.scala.akka.practice

import actors._
import akka.actor.{Props, ActorSystem}
import akka.routing.RoundRobinRouter
import collection.immutable
import java.awt.image.BufferedImage

class MutatorSystem(val name: String, val target: BufferedImage) {
  private[this] val system: ActorSystem = ActorSystem(name)

  def run() = {

    if (target == null) {
      system.log.error("Did not receive an image.")
    } else {
      system.log.info("Received image size "+target.getWidth+"x"+target.getHeight)
//      val coordinator = setUpActors
    }

    system.shutdown()
  }

  private[this]
  def setUpActors {
    // Start up selector and displayer
    val selector = system.actorOf(Props(new Selector(immutable.Vector(7, 7, 79))))
    val displayer = system.actorOf(Props[Displayer])

    // Start up mutators with round robin router
    val nrOfMutators = system.settings.config.getInt("pparkkin.scala.akka.practice.number-of-mutators")
    val router = system.actorOf(Props[Mutator].withRouter(RoundRobinRouter(nrOfInstances = nrOfMutators)))

    // Create coordinator actor
    system.actorOf(Props(new Coordinator(router, selector, displayer, nrOfMutators)))
  }
}
