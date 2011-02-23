package com.redpillsystems.jamfu.lib

import net.liftweb.common.{Empty, Box, Full}
import net.liftweb.util.{SimpleInjector}
import net.liftweb.json.JsonParser
import net.liftweb.json.JsonAST._
import java.net.URLEncoder

object FacebookConnect extends FacebookConnectImpl

trait FacebookConnectImpl extends SimpleInjector {

  lazy val apiKey = new Inject[String](System.getProperty("com.facebook.api_key")) {}
  lazy val appId = new Inject[String](System.getProperty("com.facebook.app_id")) {}
  lazy val secret = new Inject[String](System.getProperty("com.facebook.secret")) {}
  lazy val httpGet: Inject[String => Box[String]] = new Inject[String => Box[String]]((url: String) => HttpUtil.get(url)) {}

  protected def tokenUrl(code: String) =
    "https://graph.facebook.com/oauth/access_token?client_id=" + appId.vend + "&redirect_uri=" + Constants.appUrlEncoded + "&client_secret=" + secret.vend + "&code=" + code

  def token(code: String) = httpGet.vend(tokenUrl(code))

  protected def graphUrl(accessToken: String) =
    "https://graph.facebook.com/me?" + accessToken

  def user(accessToken: String): Box[FacebookUser] = httpGet.vend(graphUrl(accessToken)) match {
    case Full(s) =>
      val json = JsonParser.parse(s)
      val users = for {
        JField("id", JInt(id)) <- json
        JField("username", JString(username)) <- json
        JField("email", JString(email)) <- json
      } yield FacebookUser(id, username, email)

      users match {
        case Nil => Empty
        case u :: _ => Full(u)
      }
    case _ => Empty
  }

  def loginLink(redirect:String) =
    "https://www.facebook.com/dialog/oauth?client_id="+appId.vend+"&redirect_uri="+redirect


}

case class FacebookUser(id: BigInt, username: String, email: String)