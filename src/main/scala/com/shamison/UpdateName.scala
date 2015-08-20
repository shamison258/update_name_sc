package com.shamison

import twitter4j.{TwitterFactory, Twitter, Status}

/**
 * Created by shamison on 15/02/16.
 */
class UpdateName(status: Status) {
	val twitter: Twitter = new TwitterFactory().getInstance
	val user = twitter.showUser(Main.userId)
	val name = formatName()
	val url = user.getURL
	val loc = user.getLocation
	val des = user.getDescription

	val twitUrl = "https://twitter.com/" +
		status.getUser.getScreenName + "/status/" + status.getId

	def update(): Unit = {
		if (name.length != 0) {
			twitter.updateProfile(name, url, loc, des)
			Main.tweet("「" + name + "」に改名しました " + twitUrl)
		} else {
			Main.tweet("改名できませんでした... " + twitUrl)
		}
	}

	def formatName(): String = {
		var name = status.getText.replace("@" + Main.userId + " update_name ", "")
		name = name.replace("(@" + Main.userId + ")", "")
		name = name.replace("@", "☆")
		if (name.length <= 20 && name.length > 0)
			name
		else
			""
	}
}
