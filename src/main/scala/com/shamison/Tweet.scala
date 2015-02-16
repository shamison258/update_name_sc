package com.shamison

import twitter4j.{Twitter, TwitterFactory}

/**
 * Created by shamison on 15/02/16.
 */
class Tweet {
  val twitter: Twitter = new TwitterFactory().getInstance

  def tweet(s :String): Unit = {
    twitter.updateStatus(s)
  }
}
