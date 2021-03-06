package com.shamison

import scala.concurrent.ExecutionContext.Implicits.global
import twitter4j.{Status, UserStreamAdapter}

import scala.concurrent.Future

/**
  * Created by shamison on 15/12/31.
  */
case class Listener() extends UserStreamAdapter {

  private val updateName = """^@_sham258 update_name (.*).*""".r
  private val updateNamePrefix = """(.*).*\(@_sham258\s+?\)$""".r
//  private val updateIcon = """^@_sham258 update_icon .*""".r
  private val updateDefault = """^@_sham258 update_default.*""".r
  private val updateSon = """^(.*そん)$""".r

  override def onStatus(status: Status) = status.getText match {
    case updateName(s) => Future {
      Update.name(status, s)
    }
    case updateNamePrefix(s) => Future {
      Update.name(status, s)
    }
//    case updateIcon() => Future {
//      Update.icon(status)
//    }
    case updateDefault() => Future {
      Update.default(status)
    }
    case updateSon(s) => Future {
      Update.son(s)
    }
  }
}
