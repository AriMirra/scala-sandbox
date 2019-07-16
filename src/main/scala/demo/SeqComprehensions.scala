package demo

object SeqComprehensions extends App {

  var list: List[Int] = List(1,2,3,4,5)

  val newList: List[Int] = for (num <- list if num != 3) yield num + 1

  val ij = for (i <- 0 until 10; j <- 0 until 10 if i + j == 10) yield (i, j)

  println(ij)

}
