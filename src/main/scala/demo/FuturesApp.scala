package demo

import scala.concurrent.Future
import scala.io.{Source, StdIn}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FuturesApp extends App {

  def downloadSync(url: String): String = {
    Source.fromURL(url).getLines().mkString("\n")
  }

  Future {
    val c1 = downloadSync("https://scala-lang.org")
    println("c1: " + c1.take(50) + "\n")
  }
  Future {
    val c2 = downloadSync("http://scala-android.org")
    println("c2: " + c2.take(50) + "\n")
  }
  StdIn.readLine("Press ENTER to terminate \n")

  def downloadAsync(url: String): Future[String] = {
    Future{
      Source.fromURL(url).getLines().mkString("\n")
    }
  }

  val c1: Future[String] = downloadAsync("https://scala-lang.org")
  val c2: Future[String] = downloadAsync("https://scala-android.org")
  c1.onComplete {
    case Success(v) =>
      println("Success: " + v.take(100) + "\n")
    case Failure(e) =>
      println("Failure: " + e + "\n") // me avisa si explota
  }
  c2.foreach(v => println("Value: " + v + "\n")) // si explota no me avisa



  // el map recrea el container, le aplica la función al contenido pero
  // mantiene la estructura que le llegó (future/either/option/Try etc.)
  def countChars(url: String): Future[Int] = {
    downloadAsync(url).map(_.length)
  }

  val s1: Future[Int] = countChars("https://scala-lang.org")
  val s2: Future[Int] = countChars("https://scala-android.org")
  val s3: Future[Int] = countChars("https://scala-android.org")

  // si los quiero sumar, como son containers, debería utilizar map
  // para retener la estructura que está detrás
  val result = s1.flatMap { v1 =>
    s2.flatMap { v2 =>
      s3.map { v3 =>
        v1 + v2 + v3
      }
    }
  }

  val result2 = for {
    v1 <- s1
    v2 <- s2
    v3 <- s3
  } yield v1 + v2 + v3


  case class Person(id: String, firstName: Option[String], lastName: Option[String])

  val p = Person("a", Some("Pepe"), Some("Hands"))
  val fullname = for {
    first <- p.firstName
    last <- p.lastName
  } yield first + " " + last

  fullname.foreach(println)
}
