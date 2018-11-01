package demo

object Objects {
  trait Shape {
    def area: Int
  }

  case class Rect(h: Int, w: Int) extends Shape {
    override def area: Int = h * w
  }

  case class Triangle(h: Int, w: Int) extends Shape {
    override def area: Int = (h * w) / 2
  }

  case class GenericShapeContainer[+T <: Shape](shape: Shape) {
    def area: Int = shape.area
  }

  case class ShapeContainer(shape: Shape) {
    def area: Int = shape.area
  }

  case class P(x: Int, y: Int)

  implicit val numericP: Numeric[P] = new Numeric[P] {
    override def plus(x: P, y: P): P = P(0,0)

    override def minus(x: P, y: P): P = P(0,0)

    override def times(x: P, y: P): P = P(0,0)

    override def negate(x: P): P = P(0,0)

    override def fromInt(x: Int): P = P(0,0)

    override def toInt(x: P): Int = 0

    override def toLong(x: P): Long = 0

    override def toFloat(x: P): Float = 0

    override def toDouble(x: P): Double = 0

    override def compare(x: P, y: P): Int = 0
  }

  implicit val ordering: Ordering[P] = Ordering.by(e => e(e.x, e.y))
}


object Arithmetic {

  trait Adder[T] {
    def zero: T
    def add(a: T, b: T): T
  }

  def sum1[T](list: List[T]) (implicit adder: Adder[T]): T = {
    list.foldLeft(adder.zero)(adder.add)
  }
  def sum2[T : Adder](list: List[T]): T = {
    val adder = implicitly[Adder[T]] // polimorfismo adhoc
    list.foldLeft(adder.zero)(adder.add)
  }

  implicit val intAdder: Adder[Int] = new Adder[Int]{
    override def zero: Int = 0
    override def add(a: Int, b: Int): Int = a + b
  }

  implicit val doubleAdder: Adder[Double] = new Adder[Double]{
    override def zero: Double = 0
    override def add(a: Double, b: Double): Double = a + b
  }

}

object BoundsApp extends App {
  import Objects._
  import Arithmetic._

  val value1: GenericShapeContainer[Rect] = GenericShapeContainer(Rect(10,10))

  value1.shape.area

  val value2: ShapeContainer = ShapeContainer(Rect(10,10))

  // --- View Bounds ---
  def area[A](shape: A)(implicit c: A => Shape): Int = shape.area
  def area2(shape: Shape): Int = shape.area

  implicit def pointToShape(p: P): Shape = new Shape {
    override def area: Int = p.x + p.y
  }

  println(area(P(10,10)))
  println(area2(P(10,10)))

  val l1 = List(1, 2, 3)
  val l2 = List(1.0, 2.0, 3.0)
  val l3 = List(P(0,1), P(0,1))

  // importo las 2 bibliotecas y creo un Adder de Objetos
  implicit val pAdder: Adder[P] = new Adder[P] {
    override def zero: P = P(0,0)
    override def add(a: P, b: P): P = P(a.x + b.x, a.y + b.y)
  }
  // ahora puedo sumar ints doubles y objects pasando el adder de manera implícita
  val r1 = sum1(l1)
  val r2 = sum1(l2)
  val r3 = sum1(l3)

}

