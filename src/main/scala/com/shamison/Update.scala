package com.shamison

import java.net.{HttpURLConnection, URL}

import twitter4j.Status

/**
  * Created by shamison on 15/12/31.
  */

object Update {

  private val twitter = Tweet.twitter

  def name(status: Status, name: String) = if (name.length <= 20) {
    twitter.updateProfile(name, null, null, null)
    Tweet.tw("「%s」に改名しました。"
      .format(name).replaceAll("@", "-"), status)
  }

/*
  def icon(status: Status) = {
    val url = Option(status.getMediaEntities.apply(0).getMediaURLHttps)
    url.map(u => {
      val connection = new URL(u)
        .openConnection
        .asInstanceOf[HttpURLConnection]
      connection.setRequestMethod("GET")
      twitter.updateProfileImage(connection.getInputStream)
      Tweet.tw("アイコンを変更しました。", status)
    })
  }
 */
  def default(status: Status) = if (status.getUser.getScreenName == "_sham258") {   
    val name = "しゃみそん"
    twitter.updateProfile(name, null, null, null)
//    val in = Main.getClass.getClassLoader.getResourceAsStream("icon.png")
//    twitter.updateProfileImage(in)
    Tweet.tw("デフォルト設定に戻しました。", status)
  }

  def son(name: String) =
    if (name.length <= 20 && !name.contains("@") && !name.contains("RT"))
      twitter.updateProfile(name, null, null, null)
}
