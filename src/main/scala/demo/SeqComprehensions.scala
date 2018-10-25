package demo

object SeqComprehensions extends App {

  var list: List[Int] = List(1,2,3,4,5)
  val newList: List[Int] = for (elem <- list if elem != 3) yield elem + 1
  val ij = for (i <- 0 until 10; j <- 0 until 10 if i != j) yield (i, j)

  println(ij)

}
