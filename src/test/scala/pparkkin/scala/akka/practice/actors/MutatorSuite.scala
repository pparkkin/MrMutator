package pparkkin.scala.akka.practice.actors

import akka.testkit.{TestKit, ImplicitSender, TestActorRef}
import akka.actor.ActorSystem
import org.scalatest.{FunSuite, BeforeAndAfterAll}

class MutatorSuite(_system: ActorSystem) extends TestKit(_system) with FunSuite with BeforeAndAfterAll with ImplicitSender {
  def this() = this(ActorSystem("MutatorSuite"))

  override def afterAll {
    system.shutdown()
  }

  test("Mutator returns genetic information mutated") {
    val actorRef = TestActorRef[Mutator]
    actorRef ! Mutate(List(1, 2, 3))
    expectMsg(Mutated(List(2, 2, 3)))
  }
}
