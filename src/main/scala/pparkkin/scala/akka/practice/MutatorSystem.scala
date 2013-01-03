package pparkkin.scala.akka.practice

import actors._
import akka.actor.{Actor, Props, ActorSystem}
import akka.routing.RoundRobinRouter
import akka.dispatch.Create

class MutatorSystem(name: String, nrOfMutators: Int) {
  def run() = {
    val system = ActorSystem(name)

    // Start up selector and displayer
    val selector = system.actorOf(Props(new Selector(List(7, 7, 79))))
    val displayer = system.actorOf(Props[Displayer])

    // Start up mutators with round robin router
    val router = system.actorOf(Props[Mutator].withRouter(RoundRobinRouter(nrOfInstances = nrOfMutators)))

    // Create coordinator actor
    val coordinator = system.actorOf(Props(new Coordinator(router, selector, displayer, nrOfMutators)))

    // Begin!
    coordinator ! BeginMutating
  }

}
