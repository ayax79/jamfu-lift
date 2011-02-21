/*
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 2/13/11
 * Time: 11:10 AM
 */
package com.redpillsystems.jamfu.lib

import java.lang.String
import net.liftweb.common._
import org.apache.http.client.methods.{HttpUriRequest, HttpGet}
import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils
import org.apache.http.impl.client.DefaultHttpClient

object HttpUtil {

  private def logger = Logger(this.getClass)

  protected def http = new DefaultHttpClient

  protected def execute[T <: HttpUriRequest](method: T): Box[String] = {
    try {
      val response: HttpResponse = http.execute(method)
      EntityUtils.toString(response.getEntity) match {
        case null => Empty
        case s: String => Full(s)
      }
    }
    catch {
      case e: Exception =>
        logger.error(e.getMessage, e)
        Failure(e.getMessage)
    }
  }

  def get(url: String): Box[String] = execute(new HttpGet(url))

}



