/*
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 2/13/11
 * Time: 11:10 AM
 */
package com.redpillsystems.jamfu.lib

import org.apache.commons.httpclient.methods.GetMethod
import java.lang.String
import net.liftweb.common._
import org.apache.commons.httpclient.{HttpMethodBase, HttpClient}

object HttpUtil {

  private def logger = Logger(this.getClass)

  protected def http = new HttpClient

  protected def execute[T <: HttpMethodBase](method: T): Box[String] = {
    try {
      http.executeMethod(method)
      method.getResponseBodyAsString match {
        case null => Empty
        case s: String => Full(s)
      }
    }
    catch {
      case e: Exception =>
        logger.error(e.getMessage, e)
        Failure(e.getMessage)
    }
    finally {
      method.releaseConnection
    }
  }

  def get(url: String): Box[String] = execute(new GetMethod(url))

}



