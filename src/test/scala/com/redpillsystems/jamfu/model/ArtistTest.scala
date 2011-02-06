package com.redpillsystems.jamfu.model

import junit.framework.Assert._
import org.junit.Test

class ArtistTest extends GoogleTest {

  @Test
  def testCrud:Unit = {
    val u = new User("artisttestuser", "ArtistUserFirst", "ArtistTestUserLast", "artist@sdflkj.com")
    assertTrue(PersistenceHelper.perform(ph => u.save(ph)).isEmpty)
    assertNotNull(u.key)
    assertTrue(User.findByKey(u.key).isDefined)


    def artist = new Artist("testartist", Set(u))
    assertTrue(PersistenceHelper.perform(ph => artist.save(ph)).isEmpty)
    assertEquals(1, artist.memberKeys.size)

    assertFalse(Artist.findByMember(u).isEmpty)

    Artist.findByName(artist.name) match {
      case None => fail("should not be none")
      case Some(a1) => PersistenceHelper.perform(ph => a1.delete(ph))
    }

    assertTrue(Artist.findByName(artist.name).isEmpty)
  }


}