package com.shamison

import twitter4j._

/**
 * Created by shamison on 15/02/01.
 */
object Main {
	val userId = "_sham258"
	val tw: Tweet = new Tweet()
	val listener: StatusListener = new Listener(userId)
	val twitter: TwitterStream = new TwitterStreamFactory().getInstance()

	def main(args: Array[String]) = {
		twitter.addListener(listener)
		twitter.user()
	}
}



