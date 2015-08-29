package com.shamison

import akka.actor._
import twitter4j._

/**
 * Created by shamison on 15/02/01.
 */
object Main {
  val stream = new TwitterStreamFactory().getInstance
  stream.addListener(Listener())

  val system = ActorSystem("master")
  val actor = system.actorOf(Props[Master])

  case class NameData(s: Status, v: String)
  case class IconData(s: Status)
  case class DefData(s: Status)
  case class SonData(s: Status, v: String)

  case class Listener() extends UserStreamAdapter {
    val updateName = """^@_sham258 update_name (.*).*""".r
    val updateNamePrefix = """(.*).*\(@_sham258\s+?\)$""".r
    val updateIcon = """^@_sham258 update_icon .*""".r
    val updateDefault = """^@_sham258 update_default.*""".r
    val updateSon = """^(.*そん)$""".r

    override def onStatus(status: Status) = status.getText match {
      case updateName(s) =>
        actor ! NameData(status, s)
      case updateNamePrefix(s) =>
        actor ! NameData(status, s)

      case updateIcon() =>
        actor ! IconData(status)

      case updateDefault() if status.getUser.getScreenName == "_sham258"=>
        actor ! DefData(status)

      case updateSon(s) =>
        actor ! SonData(status, s)
    }
	}

	def main(args: Array[String]) = stream.user()
}



