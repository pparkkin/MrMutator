package pparkkin.scala.akka.practice.actors

import akka.testkit.{TestKit, ImplicitSender, TestActorRef}
import akka.actor.ActorSystem
import org.scalatest.{FunSuite, BeforeAndAfterAll}

class SelectorSuite(_system: ActorSystem) extends TestKit(_system) with FunSuite with BeforeAndAfterAll with ImplicitSender {
  def this() = this(ActorSystem("SelectorSuite"))

  override def afterAll {
    system.shutdown()
  }

  test("Selector returns a selection from two candidates") {
    val actorRef = TestActorRef[Selector]
    actorRef ! Select(List(1, 2, 3), List(4, 5, 6))
    expectMsgPF() {
      case Selected(i: List[Int]) => true
    }
  }
}
