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
    perform(ph => u.save(ph))

    System.out.println(u.key)
    assertNotNull(u.key)

    val u1 = User.findByUsername("foobar").open_!
    assertEquals(u.firstName, u1.firstName)
    perform(ph => u1.delete(ph))
    assertTrue("User should not exist", User.findByUsername("foobar").isEmpty)
  }


  @Test
  def testSaveWithErrors: Unit = {
    val u = new User
    val errors = perform(ph => u.save(ph))
    assertEquals(2, errors.length)
    assertTrue(errors.contains(RequiredError("username")))
    assertTrue(errors.contains(RequiredError("email")))
  }

}