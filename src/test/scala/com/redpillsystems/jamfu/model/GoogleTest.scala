package com.redpillsystems.jamfu.model

import com.google.appengine.tools.development.testing.{LocalServiceTestHelper, LocalDatastoreServiceTestConfig}
import org.junit.{After, Before}

trait GoogleTest {

  protected val helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());


  @Before
  def setup = helper.setUp

  @After
  def teardown = helper.tearDown

}