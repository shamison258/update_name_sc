package com.shamison

import java.net.{URL, HttpURLConnection}

import akka.actor.Actor.Receive
import akka.actor.{Actor, Props, ActorSystem}
import akka.routing.RoundRobinRouter
import twitter4j.{MediaEntity, Status, Twitter}

/**
 * Created by shamison on 15/08/20.
 */
class Master extends Actor {
  val nameRouter = context.actorOf(Props(classOf[NameActor], this).withRouter(RoundRobinRouter(2)))
  val iconRouter = context.actorOf(Props(classOf[IconActor], this).withRouter(RoundRobinRouter(2)))
  val defRouter = context.actorOf(Props(classOf[DefActor], this).withRouter(RoundRobinRouter(2)))
  val sonRouter = context.actorOf(Props(classOf[SonActor], this).withRouter(RoundRobinRouter(2)))

  override def receive = {
    case data: Main.Data => data.value match {
      case "name" => nameRouter ! data
      case "icon" => iconRouter ! data
      case "def" => defRouter ! data
      case "son" => sonRouter ! data
    }
    case x => println(x)
  }

  class SonActor extends Actor {
    override def receive = {
      case data: Main.Data => son(data.s, data.t)
    }

    def son(status: Status, twitter: Twitter) = status.getText match {
      case name if name.length <= 20 =>
        twitter.updateProfile(name, null, null, null)
    }
  }


  class DefActor extends Actor{
    override def receive = {
      case data: Main.Data => default(data.s, data.t)
    }

    def default(status: Status, twitter: Twitter) = {
      val name = "shamison"
      val twitUrl = "https://twitter.com/" +
        status.getUser.getScreenName + "/status/" + status.getId

      def update(): Unit = {
        twitter.updateProfile(name, null, null, null)
        val in = Main.getClass.getClassLoader.getResourceAsStream("icon.jpg")
        twitter.updateProfileImage(in)
        Main.tweet("デフォルト設定に戻しました." + twitUrl)
      }
    }
  }

  class NameActor extends Actor {
    override def receive = {
      case data: Main.Data => name(data.s, data.t)
    }

    def name(status: Status, twitter: Twitter): Boolean = {
      val twitUrl = "https://twitter.com/" + status.getUser.getScreenName +
        "/status/" + status.getId
      val name = status.getText
        .replaceAll("""\(@_sham258(\s+)?\)""", "")
        .replace("@_sham258 update_name ", "")
        .trim

      println(name)

      name match {
        case "" =>
          false
        case n if n.length <= 20 =>
          twitter.updateProfile(name, null, null, null)
          Main.tweet("「%s」に改名しました %s"
            .format(n, twitUrl).replaceAll("@", "-"))
          true
      }
    }
  }

  class IconActor extends Actor {
    override def receive = {
      case data: Main.Data => icon(data.s, data.t)
    }

    def icon(status: Status, twitter: Twitter): Boolean = {
      val twitUrl = "https://twitter.com/" + status.getUser.getScreenName +
        "/status/" + status.getId
      status.getMediaEntities.apply(0).getMediaURLHttps match {
        case "" | null =>
          Main.tweet("アイコンを変えられませんでした。 %s".format(twitUrl))
          false
        case s =>
          val connection = new URL(s)
            .openConnection
            .asInstanceOf[HttpURLConnection]
          connection.setRequestMethod("GET")
          twitter.updateProfileImage(connection.getInputStream)
          Main.tweet("アイコンを変更しました。 %s".format(twitUrl))
          true
      }
    }
  }

}
