/*
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 2/13/11
 * Time: 4:23 PM
 */
package com.redpillsystems.jamfu.lib

import org.specs.Specification
import org.specs.mock.Mockito
import org.specs.runner.JUnit4
import net.liftweb.common.Full

class FacebookConnecTest extends JUnit4(FacebookConnectSpec)

object FacebookConnectSpec extends Specification with Mockito {

  "facebook connect" should {

    "return valid json object" in {

      val json = "{" +
              "\"name\": \"Facebook Platform\"," +
              "\"type\": \"page\"," +
              "\"website\": \"http://developers.facebook.com\"," +
              "\"username\": \"platform\"," +
              "\"founded\": \"May 2007\"," +
              "\"company_overview\": \"Facebook Platform enables anyone to build...\"," +
              "\"mission\": \"To make the web more open and social.\"," +
              "\"products\": \"Facebook Application Programming Interface (API)...\"," +
              "\"fan_count\": 449921," +
              "\"id\": 19292868552," +
              "\"category\": \"Technology\"" +
              "\"email\": \"foo@bar.com\"" +
              "}"

      FacebookConnect.httpGet.doWith((url: String) => Full(json)) {
        System.out.println("user json -->" + FacebookConnect.user("sdlfkjfsdljk"))
      }
    }


  }


}