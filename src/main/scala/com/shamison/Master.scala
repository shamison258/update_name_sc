package com.shamison

import java.net.{URL, HttpURLConnection}

import akka.actor.Actor.Receive
import akka.actor.{Actor, Props, ActorSystem}
import akka.routing.RoundRobinRouter
import twitter4j.{TwitterFactory, MediaEntity, Status, Twitter}

/**
 * Created by shamison on 15/08/20.
 */
class Master extends Actor {
  val nameRouter = context.actorOf(Props(classOf[NameActor], this).withRouter(RoundRobinRouter(2)))
  val iconRouter = context.actorOf(Props(classOf[IconActor], this).withRouter(RoundRobinRouter(2)))
  val defRouter = context.actorOf(Props(classOf[DefActor], this).withRouter(RoundRobinRouter(2)))
  val sonRouter = context.actorOf(Props(classOf[SonActor], this).withRouter(RoundRobinRouter(2)))

  val twitter = new TwitterFactory().getInstance

  def tweet(s: String) = twitter.updateStatus(s)
  def tweet(s: String, status: Status) = twitter
    .updateStatus(s + " https://twitter.com/" +
    status.getUser.getScreenName + "/status/" + status.getId)

  override def receive = {
    case data: Main.NameData => nameRouter ! data
    case data: Main.IconData => iconRouter ! data
    case data: Main.DefData => defRouter ! data
    case data: Main.SonData => sonRouter ! data
    case x => println(x)
  }

  class SonActor extends Actor {
    override def receive = {
      case data: Main.SonData => son(data.v)
    }

    def son(name: String) = if(name.length <= 20)
      twitter.updateProfile(name, null, null, null)
  }

  class DefActor extends Actor{
    override def receive = {
      case data: Main.DefData => default(data.s)
    }
    def default(status: Status) = {
      val name = "shamison"
      twitter.updateProfile(name, null, null, null)

      val in = Main.getClass.getClassLoader.getResourceAsStream("icon.jpg")
      twitter.updateProfileImage(in)

      tweet("デフォルト設定に戻しました。", status)
    }
  }

  class NameActor extends Actor {
    override def receive = {
      case data: Main.NameData => name(data.s, data.v)
    }

    def name(status: Status, name: String): Boolean = name match {
        case "" => false
        case n if n.length <= 20 =>
          twitter.updateProfile(name, null, null, null)
          tweet("「%s」に改名しました。"
            .replaceAll("@", "-"), status)
          true
    }
  }

  class IconActor extends Actor {
    override def receive = {
      case data: Main.IconData => icon(data.s)
    }

    def icon(status: Status): Boolean = status.getMediaEntities
      .apply(0).getMediaURLHttps match {
      case "" | null =>
        tweet("アイコンを変えられませんでした。", status)
        false
      case s =>
        val connection = new URL(s).openConnection
          .asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        twitter.updateProfileImage(connection.getInputStream)
        tweet("アイコンを変更しました。", status)
        true
    }
  }
}
