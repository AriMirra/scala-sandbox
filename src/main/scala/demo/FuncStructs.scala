package demo

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

case class User(name: String, alias: Option[String] = None)

object FuncStructs extends App {

  var users: List[User] = List(
    User("Pepe"),
    User("Juan", Some("juancito")),
    User("Jose", Some("Jose88")),
  )

  val normalizedUsers: List[User] = users.map {
    u => u.copy(alias = u.alias.map(_.toLowerCase()))
  }

  for (u <- normalizedUsers) {
    val id: String = u.alias.getOrElse("undefined")
//    println(s"User: ${u.name} (alias: $id)")
  }
  def readInt: Either[Exception, Int] = {
    val in: String = StdIn.readLine("Enter an integer: ")
    try{
      Right(in.toInt)
    } catch {
      case e: Exception =>
        Left(e)
    }
  }

  def readInt2: Try[Int] = {
    val in: String = StdIn.readLine("Enter an integer: ")
    Try(in.toInt)
  }

  // usando Either, fomenta el uso del derecho
  val value1 = readInt.map(_ + 1)
  value1.foreach(println)

  // usando Try
  val value2 = readInt2.map(_ + 1)
  value2.foreach(println)
  value2 match {
    case Success(v) =>
      println("ok")
    case Failure(v) =>
      println("error")
  }
}
