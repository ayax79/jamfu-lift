package com.redpillsystems.jamfu.snippet

import net.liftweb.http.S
import net.liftweb.util.Helpers._
import xml.NamespaceBinding

object Facebook {

  implicit val fbns = new NamespaceBinding("fb", "http://facebook.com", null)

  def authlink = <div id="fb-root"></div>
          <script src="http://connect.facebook.net/en_US/all.js"></script>
          <script>
            {jsbody}
          </script>
          <fb:login-button>Login with Facebook</fb:login-button>;



  protected def url = "https://www.facebook.com/dialog/oauth?client_id=" + clientId + "&redirect_uri=" + appUrl

  protected def clientId = System.getProperty("facebook.client.id")

  protected def appUrl = urlEncode(S.request.map(_.request.url) openOr ("Undefined"))


  protected def jsbody = "FB.init({appId: " + clientId + ", cookie: true, status: true, xfbml: true});"

}