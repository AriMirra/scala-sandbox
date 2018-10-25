package demo

object Logs {
  case class Conf(indent: Int, app: String)

  def printError(msg: String)(implicit conf: Conf): Unit = {
    val indentStr = " " * conf.indent
    println(indentStr + s"[ERROR] [App: ${conf.app}] $msg")
  }

  def printInfo(msg: String)(implicit conf: Conf): Unit = {
    val indentStr = " " * conf.indent
    println(indentStr + s"[INFO] [App: ${conf.app}] $msg")
  }

  // se agrega automáticamente en el contexto que lo necesite
  implicit val conf: Conf = Conf(4, "implicitDemo")
}

object ImplicitsApp extends App {

  import Logs._

  printInfo("loading app...")
  printInfo("loading config...")
  printError("Ups...")
}



