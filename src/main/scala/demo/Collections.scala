package demo

import scala.io.Source

object Collections extends App {
  val urls = List (
    "http://scala-lang.org",
    "http://scala-android.org",
    "http://scala-lang.org",
    "http://scala-android.org",
    "http://scala-lang.org",
    "http://scala-android.org",
    "http://scala-lang.org",
    "http://scala-android.org",
    "http://google.com"
  )


  //sin el view descarga todas las urls, con el view sólo las que necesita (a lo haskell)
  val content1 = urls
    .view  // cambia la implementación de todos los métodos de abajo haciéndolos lazy
    .filter(_.endsWith(".org"))
    .map(u => {
      println(s"Downloading $u")
      Source.fromURL(u).getLines().mkString("\n")
    })

  // torna todos los métodos paralelizables
  val content2 = urls
    .par  // hace los métodos de abajo en diferentes threads --> mas rápido
    .filter(_.endsWith(".org"))
    .map(u => {
      println(s"Downloading $u")
      Source.fromURL(u).getLines().mkString("\n")
    })

  content1.take(4).foreach {c =>
    println(c.length)
  }
}
