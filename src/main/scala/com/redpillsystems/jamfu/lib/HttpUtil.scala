/*
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 2/13/11
 * Time: 11:10 AM
 */
package com.redpillsystems.jamfu.lib

import java.lang.String
import net.liftweb.common._
import java.net.URL
import org.apache.commons.io.IOUtils
import java.io.{IOException, InputStream}
import org.apache.commons.io.output.ByteArrayOutputStream

object HttpUtil {

  private val logger = Logger(this.getClass)


  def get(url: String): Box[String] = {
    val url2 = new URL(url)
    val in: InputStream = url2.openStream
    val out: ByteArrayOutputStream = new ByteArrayOutputStream
    try {
      IOUtils.copy(in, out)
    }
    catch {
      case ioe: IOException => Failure(ioe.getMessage, Full(ioe), Empty)
    }
    finally {
      in.close
      out.close
    }
    Full(new String(out.toByteArray, "UTF-8"))
  }

}



