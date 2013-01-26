package pparkkin.scala.akka.practice.actors

import akka.testkit.{TestKit, ImplicitSender, TestActorRef}
import akka.actor.{Props, ActorSystem}
import org.scalatest.{FunSuite, BeforeAndAfterAll}
import collection.immutable

class SelectorSuite(_system: ActorSystem) extends TestKit(_system) with FunSuite with BeforeAndAfterAll with ImplicitSender {
  def this() = this(ActorSystem("SelectorSuite"))

  override def afterAll {
    system.shutdown()
  }

//  test("Selector returns a selection from two candidates") {
//    val selector = system.actorOf(Props(new Selector(null)))
//    selector ! Select(immutable.Vector(0.1, 0.2, 0.3), immutable.Vector(0.4, 0.5, 0.6))
//    expectMsgPF() {
//      case Selected(i: immutable.IndexedSeq[Float]) => true
//    }
//  }
}
