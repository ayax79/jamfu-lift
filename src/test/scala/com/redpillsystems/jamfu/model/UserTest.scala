package com.redpillsystems.jamfu.model

import org.junit.Test
import com.google.appengine.api.datastore.Key
import junit.framework.Assert._

class UserTest extends GoogleTest {


  @Test
  def testSave:Unit = {
    val u = new User("foobar", "foo", "bar", "foo@bar.com")
    u.save

    val result = User.findByUsername("foobar")
    assertNotNull(result)

  }


}