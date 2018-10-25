package demo

trait Shape {
  def area: Int
}

case class Rect(h: Int, w: Int) extends Shape {
  override def area: Int = h * w
}

case class Triangle(h: Int, w: Int) extends Shape {
  override def area: Int = (h * w) / 2
}

class GenericShapeContainer[T](shape: Shape) {

}

class BoundsApp {

  val value: GenericShapeContainer[Rect] = new GenericShapeContainer(Rect(10,10))

  //TODO complete
}
