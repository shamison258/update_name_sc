package com.shamison

import twitter4j._

/**
 * Created by shamison on 15/02/01.
 */
object Main {
	def main(args: Array[String]) = {
		println("Hello, World")
		val listener: StatusListener = Listener
		val twitter: TwitterStream = new TwitterStreamFactory().getInstance()
		twitter.addListener(listener)
		twitter.user()
	}
}



