package demo

object ValueClasses extends App{

  class Meter(val value: Double) extends AnyVal {
    def +(m: Meter): Meter = new Meter(value + m.value)
  }

  // suma de objetos igual de eficiente a suma de primitivos gracias a AnyVal
  val m1  = new Meter(1.2)
  val m2  = new Meter(1.3)
  val m3  = m1 + m2

  // suma de primitivas --> alta eficiencia
  val v1 = 1.2
  val v2 = 1.3
  val v3 = v1 + v2
}

