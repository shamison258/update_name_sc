package com.shamison

import akka.actor._
import twitter4j._

/**
 * Created by shamison on 15/02/01.
 */
object Main {
  val stream = new TwitterStreamFactory().getInstance
  stream.addListener(Listener())
	def main(args: Array[String]) = stream.user()
}



