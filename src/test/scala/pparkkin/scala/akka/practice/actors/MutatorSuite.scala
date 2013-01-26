package pparkkin.scala.akka.practice.actors

import akka.testkit.{TestKit, ImplicitSender, TestActorRef}
import akka.actor.ActorSystem
import org.scalatest.{FunSuite, BeforeAndAfterAll}
import collection.immutable

class MutatorSuite(_system: ActorSystem) extends TestKit(_system) with FunSuite with BeforeAndAfterAll with ImplicitSender {
  def this() = this(ActorSystem("MutatorSuite"))

  override def afterAll {
    system.shutdown()
  }

//  test("Mutator returns genetic information mutated") {
//    val actorRef = TestActorRef[Mutator]
//    actorRef ! Mutate(immutable.Vector(0.1, 0.2, 0.3))
//    expectMsgPF() {
//      case Mutated(i: immutable.Vector[Float]) => true
//    }
//  }
}
