package com.shamison

import akka.actor._
import akka.pattern.ask
import akka.routing.RoundRobinRouter
import twitter4j._

/**
 * Created by shamison on 15/02/01.
 */
object Main {
	val twitter = new TwitterFactory().getInstance
  val stream = new TwitterStreamFactory().getInstance
  stream.addListener(Listener())

  val system = ActorSystem("master")
  val actor = system.actorOf(Props[Master])

  case class Data(t: Twitter, s: Status, value: String)

  case class Listener() extends UserStreamAdapter {
    val updateName = """^@_sham258 update_name .*""".r
    val updateNamePrefix = """.*\(@_sham258\s?\)$""".r
    val updateIcon = """^@_sham258 update_icon .*""".r
    val updateDefault = """^@_sham258 update_default.*""".r
    val updateSon = """.*そん$""".r

    override def onStatus(status: Status) = status.getText match {
      case updateName() | updateNamePrefix() =>
        actor ! Data(twitter, status, "name")
      case updateIcon() =>
        actor ! Data(twitter, status, "icon")
      case updateDefault() if status.getUser.getScreenName == "_sham258"=>
        actor ! Data(twitter, status, "def")
      case updateSon() =>
        actor ! Data(twitter, status, "son")
    }
	}
  
	def tweet(s: String) = twitter.updateStatus(s)

	def main(args: Array[String]) = stream.user()


}



