package pparkkin.scala.akka.practice

/**
 * @author ${user.name}
 */
object MrMutator {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    println("concat arguments = " + foo(args))
  }

}
