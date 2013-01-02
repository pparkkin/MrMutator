package pparkkin.scala.akka.practice

import actors.{Mutate, Mutator}
import akka.actor.{Props, ActorSystem}

object MrMutator {
  
  def main(args : Array[String]) {
    val system = ActorSystem("MrMutator")
    val mutator = system.actorOf(Props[Mutator])
    mutator ! Mutate(List(1, 2, 3))
    system.shutdown()
  }

}
