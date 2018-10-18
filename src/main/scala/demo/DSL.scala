package demo

// break no existe en scala, acá lo implementamos nosotros
object DSL extends App{

  import Break._

  breakable{
    for (i <- 1 to 100) {
      println(i)

      if (i == 10) {
        edu
      }
    }
  }

  object Break {
    private  class BreakException extends RuntimeException
    private val bExc = new BreakException

    def breakable(op: => Unit): Unit = {
      try {
        op
      }
      catch {
        case ex: BreakException =>
      }
    }

    def edu: Unit = {
      throw bExc
    }
  }

}
