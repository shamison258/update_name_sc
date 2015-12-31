package com.shamison

import twitter4j.{Status, TwitterFactory}

/**
  * Created by shamison on 15/12/31.
  */
object Tweet {
  val twitter = new TwitterFactory().getInstance
  def tw(s: String) = twitter.updateStatus(s)
  def tw(s: String, status: Status) = twitter
    .updateStatus(s + " https://twitter.com/" +
      status.getUser.getScreenName + "/status/" + status.getId)
}
