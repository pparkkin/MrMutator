package pparkkin.scala.akka.practice

import org.scalatest.FunSuite

class MrMutatorSuite extends FunSuite {

  test("MrMutator.foo should return empty string for empty Array") {
    assert(MrMutator.foo(Array()) == "")
  }

}
