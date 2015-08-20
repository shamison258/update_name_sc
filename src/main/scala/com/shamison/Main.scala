package com.shamison

import twitter4j._

/**
 * Created by shamison on 15/02/01.
 */
object Main {
	val userId = "_sham258"
	val twitter = new TwitterStreamFactory().getInstance
  twitter.addListener(Listener(userId))

  case class Listener(userId: String) extends UserStreamAdapter {
    val updateName = """^@_sham258 update_name .*""".r
    val updateNamePrefix = """.*(@_sham258)$""".r
    val updateIcon = """^@_sham258 update_icon .*""".r
    val updateDefault = """^@_sham258 update_default .*""".r

    override def onStatus(status: Status): Unit = {
			status.getText match {
        case updateName() => new UpdateName(status).update()
        case updateNamePrefix() => new UpdateName(status).update()
        case updateIcon() => new UpdateIcon(status).update()
        case updateDefault() => new UpdateDefault(status).update()
      }
		}
	}

	def tweet(s: String) = new TwitterFactory().getInstance.updateStatus(s)

	def main(args: Array[String]) = twitter.user()
}



