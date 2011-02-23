package com.redpillsystems.jamfu.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmd
import net.liftweb.http.{SHtml, SessionVar, S}
import com.redpillsystems.jamfu.model.User
import net.liftweb.http.js.JE._
import xml.{Text, NodeSeq}
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.SHtml._
import com.redpillsystems.jamfu.lib.{FacebookUser, Constants, FacebookConnect}
import net.liftweb.common.{Logger, Empty, Box, Full}

class ConnectSnippet {

  private val logger = Logger(this.getClass)

  object facebookSession extends SessionVar[Box[FacebookUser]](Empty)

  def redirectRoot = RedirectTo("/")

  def facebookLogin(in: NodeSeq): NodeSeq = {

    facebookSession.set(S.param("code") match {
      case Full(code) =>
        logger.error("Found code" + code)

        FacebookConnect.token(code) match {
          case Full(token) => FacebookConnect.user(token)
          case _ => Empty
        }
      case _ =>
        logger.error("No code was found")
        Empty
    })

    facebookSession.get match {
      case Full(fu: FacebookUser) => <span span="hello">Hello {fu.username}</span>;
      case _ =>
        val url: String = FacebookConnect.loginLink(Constants.appUrl)
        <a href={url}>Login With Fb</a>;
    }
  }


  //  def initfb: NodeSeq = {
  //    Script(Call("FB.init", Str(FacebookRestApi.apiKey), Str("/xd_receiver.html")))
  //  }

  //  def fbconnectLink(in: NodeSeq): NodeSeq = {
  //    val user = User.currentUser.open_! //must be logged in
  //    if (user.fbid.is == 0)
  //      SHtml.a(Text("Link your account with fb"), connectCmd)
  //    else
  //      bind("f", in,
  //        AttrBindParam("uid", Text(user.fbid.is.toString), "uid"),
  //        "unlink" -> SHtml.a(() => {
  //          user.fbid(0).save
  //          Alert("Unlinked") & redirectRoot
  //        }, Text("Unlink from facebook"))
  //      )
  //  }


  //  def requireSession(f: FacebookSession => JsCmd): JsCmd = {
  //    val ajaxLogin = SHtml.ajaxInvoke(() => {
  //      FacebookConnect.session match {
  //        case Full(session) => f(session)
  //        case _ => Alert("Failed to create Facebook session")
  //      }
  //    })._2
  //
  //    Call("FB.Connect.requireSession", AnonFunc(ajaxLogin))
  //  }

  //  def loginCmd: JsCmd = requireSession {
  //    session =>
  //      User.findByFbId(session.uid) match {
  //        case Full(user) =>
  //          User.logUserIn(user)
  //          S.notice("You're now logged in")
  //          redirectRoot
  //        case _ =>
  //          S.error("You need to register first")
  //          RedirectTo("/user_mgt/sign_up")
  //      }
  //  }

  //  def connectCmd: JsCmd = requireSession {
  //    session =>
  //      val user = User.currentUser.open_!
  //      user.fbid(session.uid.toLong).validate match {
  //        case Nil =>
  //          user.save
  //          S.notice("Linked to facebook connect")
  //        case xs => S.error(xs)
  //      }
  //      redirectRoot
  //  }
}