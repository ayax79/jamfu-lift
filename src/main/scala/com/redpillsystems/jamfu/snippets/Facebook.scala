package com.redpillsystems.jamfu.snippets

import net.liftweb.util.Helpers._
import net.liftweb.http._
import xml.{Text, NodeSeq}

class Facebook {

  def login(xhtml : NodeSeq) : NodeSeq = "<a href=\"" + url +"\">Login Via Facebook</a>"

  protected def url = "https://www.facebook.com/dialog/oauth?client_id="+ clientId + "&redirect_uri=" + appUrl

  protected def clientId = "todoid"

  protected def appUrl = "todourl"


}