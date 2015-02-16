package com.shamison

import twitter4j._


/**
 * Created by shamison on 15/02/14.
 */
class Listener extends UserStreamAdapter {
	override def onStatus(status: Status): Unit = {
		println(status.getUser.getName
			+ "(@" + status.getUser.getScreenName + ")"+ ": ")
		println(status.getText)
		println()
	}

	override def onFavorite(source: User, target: User, favoritedStatus: Status){
		println("[FAV]:"+ source.getName
			+ "(" + source.getScreenName + ")"+ " to ")
		println(favoritedStatus.getText)
		println()
	}

}
