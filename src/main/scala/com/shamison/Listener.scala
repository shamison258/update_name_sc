package com.shamison

import twitter4j._


/**
 * Created by shamison on 15/02/14.
 */
class Listener(userId: String) extends UserStreamAdapter {

	override def onStatus(status: Status): Unit = {
		val tlText = status.getText

		if (tlText.startsWith("@" + userId + " update_name ")
			|| tlText.endsWith("(@" + Main.userId + ")"))
			if (!tlText.startsWith("RT"))
				new UpdateName(status).update()
		if(tlText.startsWith("@" + userId + " update_icon "))
			new UpdateIcon(status).update()

		if(tlText.startsWith("@"+ userId+ " update_default")
			&& status.getUser.getScreenName == userId)
			new UpdateDefault(status).update()
	}

}
