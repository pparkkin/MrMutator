package pparkkin.scala.akka.practice

import actors._
import akka.actor.{ActorRef, Props, ActorSystem}
import akka.routing.RoundRobinRouter
import collection.immutable
import java.awt.image.BufferedImage
import model.{DebugInformation, GeneticMaterial}
import swing.Panel

class MutatorSystem(val name: String, val genepool: GeneticMaterial,  panel: Panel, di: DebugInformation) {
  val system: ActorSystem = ActorSystem(name)

  def run() {

//    while (true) {
//      data.data = GeneticMaterial.random(data.data.length).data
//      panel.repaint()
//      Thread.sleep(1000)
//    }

    val (mutator, _, _) = setUpActors
    mutator ! Mutate

//    system.shutdown()
  }

  def setUpActors: (ActorRef, ActorRef, ActorRef) = {
    // Start up selector and displayer
    val displayer = system.actorOf(Props(new Displayer(panel)))
    val selectorCounter = system.actorOf(Props(new Counter(di, "Selected")))
    val selector = system.actorOf(Props(new Selector(genepool, displayer, selectorCounter)))

    // Start up mutator
    val mutatorCounter = system.actorOf(Props(new Counter(di, "Mutations")))
    val mutator = system.actorOf(Props(new Mutator(genepool, selector, mutatorCounter)))

    (mutator, selector, displayer)
  }
}
