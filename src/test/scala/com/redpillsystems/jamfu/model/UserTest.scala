package com.redpillsystems.jamfu.model

import org.junit.Test
import junit.framework.Assert._
import PersistenceHelper._
import net.liftweb.common.Empty
import net.liftweb.common.Full

class UserTest extends GoogleTest {


  @Test
  def testSave: Unit = {
    val u = new User("foobar", "foo", "bar", "foo@bar.com")
    u.save

    System.out.println(u.key.is)
    assertNotNull(u.key.is)

    val u1 = User.findByUsername("foobar").open_!
    assertEquals(u.firstName.is, u1.firstName.is)
    u1.delete_!
    assertTrue("User should not exist", User.findByUsername("foobar").isEmpty)
  }


}