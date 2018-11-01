package demo.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class HelloWorld extends Actor {
  override def receive: Receive = {
    case msg =>
      println(msg)
  }
}

object Test extends App {
  val system = ActorSystem("mySystem")
  val ref: ActorRef = system.actorOf(Props[HelloWorld], "hello")
  ref ! "hello" //ref.!("hello")
  println(ref)

}