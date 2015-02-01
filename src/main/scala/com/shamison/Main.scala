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

object Listener extends UserStreamAdapter {
	override def onStatus(status: Status): Unit = {
		print(status.getUser.getName + ": ")
		println(status.getText)
	}

	override def onFavorite(source: User, target: User, favoritedStatus: Status){
		print(source.getName + " fav to ")
		println(target.getName)
	}



}

