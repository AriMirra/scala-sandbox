package tp1

import scala.io.Source

object Freq {
  def freq(fileName: String): List[(String, Int)] =
    Source.fromFile(fileName).getLines().toList
      .map(_.toLowerCase)
      .flatMap(_.split("[^a-z]"))
      .filter(_.nonEmpty)
      .groupBy(e => e)
      .map { case (s,ss) => (s, ss.length) }
      .toList
}
