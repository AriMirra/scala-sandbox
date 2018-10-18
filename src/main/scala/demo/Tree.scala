package demo

trait Tree {
  def sum: Int
}

case class Branch(left: Tree, right: Tree) extends Tree {
  override def sum: Int = left.sum + right.sum
}
case class Leaf(value: Int) extends Tree {
  override def sum: Int = value
}

object TreeApp extends App {

  val tree = Branch(Leaf(1), Leaf(2))
  println(tree.sum)
}

