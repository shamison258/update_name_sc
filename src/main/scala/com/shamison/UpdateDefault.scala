package com.shamison

import twitter4j.{Status, TwitterFactory, Twitter}

/**
 * Created by shamison on 15/04/21.
 */
class UpdateDefault(status: Status) {
	val twitter: Twitter = new TwitterFactory().getInstance
	val user = twitter.showUser(Main.userId)
	val name = "shamison"
	val url = user.getURL
	val loc = user.getLocation
	val des = user.getDescription

	val twitUrl = "https://twitter.com/" +
		status.getUser.getScreenName + "/status/" + status.getId

	def update(): Unit = {
		twitter.updateProfile(name, url, loc, des)
		val in = Main.getClass.getClassLoader.getResourceAsStream("icon.jpeg")
		twitter.updateProfileImage(in)
		new Tweet().tweet("デフォルト設定に戻しました." + twitUrl)
	}

}
