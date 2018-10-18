package demo

sealed trait IntTree {
  def sum: Int
}

case class IntBranch(left: IntTree, right: IntTree) extends IntTree {
  override def sum: Int = left.sum + right.sum
}
case class intLeaf(value: Int) extends IntTree {
  override def sum: Int = value
}

object IntTreeApp extends App {

  val tree = IntBranch(intLeaf(1), intLeaf(2))

  println(tree.sum) // sum le pertenece al árbol, funcional

  def sumLeaves(t: IntTree): Int = t match {
    case IntBranch(l,r) => sumLeaves(l) + sumLeaves(r)
    case intLeaf(v) => v
  }

  println(sumLeaves(tree)) //es un contador de hojas, no le responsabiliza al árbol saber contar sus hojas, oop
}



sealed trait Tree[+T]

case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]
case class Leaf[T](value: T) extends Tree[T]

object TreeApp extends App {

  def printTree(t: Tree[Any]): Unit = t match {
    case Branch(l, r) =>
      print("Left:")
      printTree(l)
      println("")
    case Leaf(v) =>
      println(s"Value: $v")
  }

  val tree: Tree[Int] = Branch(
    Branch(
      Leaf(1), Leaf(2)
    ),
    Leaf(4)
  )

  val treeS: Tree[String] = Leaf("test")

  printTree(tree)
}

