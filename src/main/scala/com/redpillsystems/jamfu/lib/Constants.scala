/*
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 2/13/11
 * Time: 10:42 AM
 */
package com.redpillsystems.jamfu.lib

import java.net.URLEncoder
;
object Constants {

  lazy val appUrl = System.getProperty("app.url")

  lazy val appUrlEncoded = URLEncoder.encode(appUrl, "UTF-8")



}