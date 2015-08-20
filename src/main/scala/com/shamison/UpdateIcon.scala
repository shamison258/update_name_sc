package com.shamison

import java.net.{HttpURLConnection, URL}
import twitter4j.{TwitterFactory, Twitter, Status}

/**
 * Created by shamison on 15/04/21.
 */
class UpdateIcon (status: Status) {
	val twitter: Twitter = new TwitterFactory().getInstance
	val user = twitter.showUser(Main.userId)
	val twitUrl = "https://twitter.com/" +
		status.getUser.getScreenName + "/status/" + status.getId

	def update(): Unit = {
		val medias = status.getMediaEntities
		val url = new URL(medias(0).getMediaURLHttps)
		val connection = url.openConnection().asInstanceOf[HttpURLConnection]
		connection.setRequestMethod("GET")
		val in = connection.getInputStream
		twitter.updateProfileImage(in)

		Main.tweet(
			".@%s さんがアイコンを変えました %s".format(status.getUser.getScreenName, twitUrl))
	}
}
