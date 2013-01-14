package pparkkin.scala.akka.practice

object MrMutator {
  
  def main(args : Array[String]) {
    val mutatorSystem = new MutatorSystem("MrMutator")
    mutatorSystem.run()
  }

}
