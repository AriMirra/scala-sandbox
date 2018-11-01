package demo.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class Count(value: Int)

case class Stats(total: Int)

case object GetStats

/**
  * Counter actor
  */
class Counter extends Actor {
  var total: Int = 0
  override def receive: Receive = {
    case Count(value) =>
      total += value
    case GetStats =>
      sender ! Stats(total) //el sender se guarda autmáticamente de forma implícita
  }
}

/**
  * Printer actor
  */
class Printer extends Actor {
  override def receive: Receive = {
    case msg =>
      println(s"Msg: $msg")
  }
}


object Counter extends App {
  val system = ActorSystem("mySystem")
  val counter: ActorRef = system.actorOf(Props[Counter], "counter")
  val printer: ActorRef = system.actorOf(Props[Printer], "printer")

  counter ! Count(10)
  counter ! Count(20)
  counter ! Count(5)
  counter ! GetStats

  counter.tell(GetStats, printer) //combino ambos actores para que se hablen entre sí
}
