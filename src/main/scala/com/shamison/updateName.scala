package com.shamison

import twitter4j.{TwitterFactory, Twitter, Status}

/**
 * Created by shamison on 15/02/16.
 */
class updateName(status: Status) {
  val twitter: Twitter = new TwitterFactory().getInstance
  val user = twitter.showUser(Main.userId)
  val name = formatName()
  val url = user.getURL
  val loc = user.getLocation
  val des = user.getDescription

  def update(): Unit = {
    twitter.updateProfile(name, url, loc, des);
    new Tweet().tweet("「" + name + "」に改名しました.")
  }

  def formatName(): String = {
    var name = status.getText.replace("@"+Main.userId + " update_name ", "")
    name = name.replace("(@"+Main.userId+")", "")
    if (name.length <= 20 && name.length > 0)
      return name
    else
      return null
  }
}
