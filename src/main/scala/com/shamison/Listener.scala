package com.shamison

import twitter4j._


/**
 * Created by shamison on 15/02/14.
 */
class Listener(userId:String) extends UserStreamAdapter {

  override def onStatus(status: Status): Unit = {
//		println(status.getUser.getName
//			+ "(@" + status.getUser.getScreenName + ")"+ ": ")
//		println(status.getText)
//		println()
    val tlText = status.getText
    if (tlText.startsWith("@"+userId +" update_name ") ||
      tlText.endsWith("(@"+Main.userId+")"))
      new updateName(status).update()
	}

//	override def onFavorite(source: User, target: User, favoritedStatus: Status){
//		println("[FAV]:"+ source.getName
//			+ "(" + source.getScreenName + ")"+ " to ")
//		println(favoritedStatus.getText)
//		println()
//	}

}
