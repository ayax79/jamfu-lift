/*
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 2/23/11
 * Time: 8:24 AM
 */
package com.redpillsystems.jamfu.snippet

import xml.NodeSeq

object SettingsSnippet {


  def settings: NodeSeq =
    <ul>
      <li>app.url: {System.getProperty("app.url")}</li>
      <li>com.facebook.app_id: {System.getProperty("com.facebook.app_id")}</li>
      <li>com.facebook.api_key {System.getProperty("com.facebook.api_key")}</li>
      <li>com.facebook.secret {System.getProperty("com.facebook.secret")}</li>
    </ul>;

}