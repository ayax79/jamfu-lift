package com.redpillsystems.jamfu.model

import org.junit.Test
import com.google.appengine.api.datastore.Key
import junit.framework.Assert._

class UserTest extends GoogleTest {


  @Test
  def testSave:Unit = {
    val u = new User("foobar", "foo", "bar", "foo@bar.com")
    u.save

    User.findByUsername("foobar") match {
      case None => fail("should of returned a result")
      case Some(u1) => assertEquals(u.firstName, u1.firstName)
    }


  }


}