package demo.actors.Crawler

import java.net.{MalformedURLException, URL}

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool
import com.sun.webkit.WebPage

import scala.util.Try

/**
  * Protocol
  */
//case class FetchURL(url: String)
//
//case class FetchedURL(url: String, content: String)

case class Parse(url: String, content: String)

case class Parsed(url: String, page: WebPage)

/**
  * Parser actors
  */
class Parser extends Actor {
  val workers: ActorRef = context.actorOf(
    RoundRobinPool(4).props(Props[ParseWorker])
  )

  override def receive: Receive = {
    case msg: FetchURL =>
      workers.forward(msg) //forward maintains the original sender to the forwarded actor
  }
}

class ParseWorker extends Actor {

  import scala.collection.JavaConverters._

  override def receive: Receive = {
    case Parse(url, html) =>
      try {
        val current = new URL(url)

        val doc = org.jsoup.Jsoup.parse(html)

        val links = doc.select("a[href]")
          .asScala
          .map(_.attr("href"))

        val urls = links.flatMap(l =>
          Try(new URL(current, l)).toOption)
          .map(_.toString.takeWhile(_ != '#'))
          .distinct
          .sorted

      } catch {
        case _: MalformedURLException =>
      }
  }
}
