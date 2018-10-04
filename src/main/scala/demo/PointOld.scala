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
  def apply(_x: Int, _y: Int): PointOld = new PointOld(_x, _y)

  def minX(p1: PointOld, p2: PointOld): PointOld = {
    if (p1.x < p2.x) p1 else p2
  }

}


// define el constructor y los fields al mismo tiempo
// implementa el equals, toString, apply, pattern matching, etc.
case class Point(x: Int, y: Int)

object PointApp extends App {
  val o1 = PointOld(1, 2)

  val c = Point(3,3)
  val d = Point(3,3)

  val str = c match {
    case Point(0, 0) => "origen"
    case Point(0, _) => "eje"
    case Point(_, 0) => "eje"
    case Point(x, y) => s"x: $x, y: $y"
  }

  println(str)
  println(c == d)
}
