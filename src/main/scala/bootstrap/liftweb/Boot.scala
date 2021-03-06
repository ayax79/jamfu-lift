/*
 * Copyright 2008 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package bootstrap.liftweb

import _root_.net.liftweb.http._
import net.liftweb.util.LoanWrapper
import com.redpillsystems.jamfu.model.User
import net.liftweb.common.Full
import provider.HTTPRequest
import net.liftweb.sitemap.{Menu, Loc, SiteMap}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    LiftRules.useXhtmlMimeType = false // because facebook sucks

    // where to search for snippet, views, etc
    LiftRules.addToPackages("com.redpillsystems.jamfu")

    val entries = Menu(Loc("Home", List("index"), "Home")) :: Menu(Loc("Settings", List("settings"), "Settings")) :: Nil
    LiftRules.setSiteMap(SiteMap(entries:_*))

    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart =
            Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd =
            Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    //this is optional. Provides SSO for users already logged in to facebook.com
//    S.addAround(List(new LoanWrapper {
//      def apply[N](f: => N): N = {
//        if (!User.loggedIn_?) {
//          for (c <- FacebookConnect.client; user <- User.findByFbId(c.session.uid)) {
//            User.logUserIn(user)
//          }
//        }
//        f
//      }
//    }))

    //this is really important for fb connect
    LiftRules.useXhtmlMimeType = false

    LiftRules.liftRequest.append {
      case Req("xd_receiver" :: Nil, _, _) => false
    }
  }

  /**
   * Force the request to be UTF-8
   */
  private def makeUtf8(req: HTTPRequest): Unit = {
    req.setCharacterEncoding("UTF-8")
  }
}

