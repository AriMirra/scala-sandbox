package demo

object Strings {
  //clase anidada dentro del objeto, recibe un solo argumento y amplifica la funcionalidad de la clase del mismo
  implicit class Str(str: String) {
    def wow: String = str.toUpperCase
  }
}

object Booleans {
  implicit class MyBool(x: Boolean) {
    def and(that: Boolean): MyBool = if (x) that else this
    def or(that: Boolean): MyBool = if (x) this else that
    def negate: MyBool = new MyBool(!x)
  }
}

object ImplicitClasses extends App {

  import Strings._
  import Booleans._

  val s = "hello"
  //le agregué funcionalidad a la clase String, sin modificar la clase string
  val sWow2 = s.wow

  val b1 = true
  val b2 = false
  val b3 = true

  println(b1 and b2 or b3)
}
