package com.redpillsystems.jamfu.snippet

import net.liftweb.util.Helpers._
import xml.NodeSeq
import net.liftweb.http.{SessionVar, S}

object Facebook {

//  object sessionUser extends SessionVar[User]


  def auth (in: NodeSeq): NodeSeq = {

    null


  }



  def authlink = <span class="facebook-link"><a href={url}>Login with Facebook</a></span>

  protected def url = "https://www.facebook.com/dialog/oauth?client_id=" + clientId + "&redirect_uri=" + appUrl

  protected def clientId = System.getProperty("facebook.client.id")

  protected def appUrl = urlEncode(S.request.map(_.request.url) openOr ("Undefined"))

}