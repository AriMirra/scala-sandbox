package demo

class PointOld(_x: Int, _y: Int) {

  require(_x >= 0)
  require(_y >= 0)

  val x: Int = _x
  val y: Int = _y

  println("Created Point" + x + ", " + y)

  def add(that: PointOld): PointOld = new PointOld(x + that.x, y + that.y)

  override def toString: String = "Point(" + x + ", " + y + ")"
}

object PointOld {

  val ZERO = new PointOld(0, 0)

  def apply(_x: Int, _y: Int): PointOld = if(_x == 0 && _y == 0)
    ZERO else new PointOld(_x, _y)

  def minX(p1: PointOld, p2: PointOld): PointOld = {
    if (p1.x < p2.x) p1 else p2
  }

}


// define el constructor y los fields al mismo tiempo
// implementa el equals, toString, apply, pattern matching, etc.
case class Point(x: Int, y: Int) {
  def sum: Int = x + y
  def add(p: Point): Point = Point(x + p.x, y + p.y)
  def +(p: Point): Point = Point(x + p.x, y + p.y)
  def minX(p1: Point, p2: Point): Int = if (p1.x < p2.x) p1.x else p2.x
}

object PointApp extends App {
  val o1 = PointOld(1, 2)
  val o2 = new PointOld(1, 2)
  val o3 = PointOld.minX(o1, o2)

  val zero = PointOld.ZERO //puedo llamar a las variables de la clase

  val a = Point(0,5)
  val b = Point(3,3)
  val c = b.copy(x = 2)

  val sum1 = a.add(b)
  val sum2 = a add b

  val sum3 = a.+(b)
  val sum4 = a + b

  val str = c match {
    case Point(0, 0) => "origen"
    case Point(0, _) => "eje"
    case Point(_, 0) => "eje"
    case Point(x, y) => s"x: $x, y: $y"
  }

  println(str)
  println(a == b)
}
