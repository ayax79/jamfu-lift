package com.redpillsystems.jamfu.model

import org.junit.Test
import com.google.appengine.api.datastore.Key

class UserTest extends GoogleTest {


  @Test
  def testSave:Unit = {
    val u = new User("foobar", "foo", "bar", "foo@bar.com")
    u.save

  }


}