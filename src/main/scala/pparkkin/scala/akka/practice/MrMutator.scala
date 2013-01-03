package pparkkin.scala.akka.practice

import actors.{Mutate, Mutator}
import akka.actor.{Props, ActorSystem}

object MrMutator {
  
  def main(args : Array[String]) {
    val mutatorSystem = new MutatorSystem("MrMutator", 3)
    mutatorSystem.run()
  }

}
