package com.redpillsystems.jamfu.model

import org.junit.Test
import junit.framework.Assert._

class UserTest extends GoogleTest {


  @Test
  def testSave:Unit = {
    val u = new User("foobar", "foo", "bar", "foo@bar.com")
    u.save

    System.out.println(u.key)
    assertNotNull(u.key)

    User.findByUsername("foobar") match {
      case None => fail("should of returned a result")
      case Some(u1) =>
        assertEquals(u.firstName, u1.firstName)
        u1.delete
        assertTrue("User should not exist", User.findByUsername("foobar").isEmpty)
    }

  }


}