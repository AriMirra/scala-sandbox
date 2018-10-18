package demo

import scala.io.Source

object DemoApp extends App {
  val lines = Source.fromFile("/Users/arielmirra/Documents/sandbox/scala-sandbox/src/main/test.txt").getLines().toList

  val words = lines
    .map(_.toLowerCase)
    .flatMap(_.split("[^a-z]").toList)
    .filter(_.nonEmpty)

  val freq = words
    .groupBy(e => e)
    .map{
      case (word, list) =>
        word -> list.length
    }

  val top = freq.toList.sortBy(- _._2)

  top.take(10).foreach(println)
}
