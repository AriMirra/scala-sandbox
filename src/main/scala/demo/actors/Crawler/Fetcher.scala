package demo.actors.Crawler

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import demo.actors.Crawler.Fetcher.system

import scala.io.Source

/**
  * Protocol
  */
case class FetchURL(url: String)

case class FetchedURL(url: String, content: String)

case class Crawl(url: String, maxPages: Int)

case class FoundPage()
/**
  * Fetcher actors
  */
class Fetcher extends Actor {
  val workers: ActorRef = context.actorOf(
    RoundRobinPool(4).props(Props[FetchWorker])
  )

  override def receive: Receive = {
    case msg: FetchURL =>
      workers.forward(msg) //forward maintains the original sender to the forwarded actor
  }
}

class FetchWorker extends Actor {
  override def receive: Receive = {
    case FetchURL(url) =>
      try {
        println(s"Downloading $url")
        val content = Source.fromURL(url).getLines().mkString("\n")
        sender() ! FetchedURL(url, content)
      } catch {
        case e: Exception => println(e.toString)
      }
  }
}

class CrawlerPrinter extends Actor {
  override def receive: Receive = {
    case FetchedURL(url, content) =>
      println(s"[Printer] Fetched URL: $url content: ${content.take(20)}...")
    case Parsed(url, page) =>
//      println(s"[Printer] Parsed $url with ${page.links.size} links")
  }
}

object Fetcher extends App {
  val system = ActorSystem("crawler")
  val fetcher = system.actorOf(Props[Fetcher], "fetcher")
  val crawlerPrinter = system.actorOf(Props[CrawlerPrinter], "crawlerPrinter")

//  fetcher.tell(FetchURL("http://scala-lang.org"), crawlerPrinter)
//  fetcher.tell(FetchURL("http://scala-android.org"), crawlerPrinter)
}

class Crawler extends Actor {

  val fetcher = system.actorOf(Props[Fetcher], "fetcher")
  val parser = system.actorOf(Props[Parser], "parser")

  var root: String = _
  var originalSender: ActorRef = _

  var pending: Set[String] = Set()
  var processed: Set[String] = Set()

  override def receive: Receive = {
    case Crawl(url, maxPages) =>
      root = url
      originalSender = sender()
      fetcher ! FetchURL(url)
    case FetchedURL(url, content) =>
      parser ! Parse(url, content)

    case Parsed(url, webPage) =>
//      originalSender ! FoundPage(root, url, webPage)
//
//      pending = pending ++ (webPage.links.toSet -- processed)

      pending.takeRight(10).foreach{ p =>
        processed += p
        fetcher ! FetchURL(p)
      }

      pending.dropRight(10)

      // TODO complete
  }
}
