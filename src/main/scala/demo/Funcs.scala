package demo

import scala.collection.immutable

object Funcs extends App{
  def mul(n1: Int, n2: Int): Int = n1 * n2
  def mul2(n1: Int) (n2: Int): Int = n1 * n2  // partial function application

  mul(2,2)
  mul2(2)(2)

  val double: Int => Int = mul2(2)(_)
  val double2: Function1[Int, Int] = double

  def f1(p: Int => Int) = println("p: " + p(5))

  val myFunction: Int => Int = (n: Int) => n + 1

  println(f1(myFunction))
  println(f1((n: Int) => n + 1))
  println(f1(n => n + 1))
  println(f1(_ + 1))

  // partial function
  val partialFuncion: PartialFunction[Any, Int] = {
    case n: Int =>
      n + 2
    case s: String =>
      s.length
  }

  def completeFunction(v: Any): Int = {
    v match {
      case n: Int =>
        n + 2
      case s: String =>
        s.length
    }
  }

  // en la función completa no puedo saber si va a fallar o no
  println(completeFunction(2))

  // puedo fijarse si está definida
  if(partialFuncion.isDefinedAt(true)) partialFuncion(true)

  // puedo recolectar los valores que están definidos dentro de una colección
  val result: immutable.Seq[Int] = List("hello", 12, true, 4).collect(partialFuncion)

  println(result)



  // Functions by value & name
  def f1(p: Int) = {
    println("f1.start")

//    p
//    p

    println("f1.end")
  }

  def f2(p: () => Int) = {
    println("f2.start")

    p()
    p()

    println("f2.end")
  }
  f2(() => {
    println("Generating value for f2...")
    10
  })

  def f3(p: => Int) = {
    println("f3.start")

//    p()
//    p()

    println("f3.end")
  }
  f3(10) //si es una función bien, sino lo envuelve en una función automáticamente

  def f4(p: => Int, cond: Boolean) = {
    println("f3.start")

    if (cond) p

    println("f3.end")
  }



}
