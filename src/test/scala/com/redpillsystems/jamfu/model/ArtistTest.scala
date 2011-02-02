package com.redpillsystems.jamfu.model

import junit.framework.Assert._
import org.junit.Test


class ArtistTest extends GoogleTest {

  @Test
  def testCrud:Unit = {
    val u = new User("artisttestuser", "ArtistUserFirst", "ArtistTestUserLast", "artist@sdflkj.com")
    u.save

    def artist = new Artist("testartist", Set(u))
    artist.save
    assertEquals(1, artist.memberKeys.size)

    assertFalse(Artist.findByMember(u).isEmpty)

    Artist.findByName(artist.name) match {
      case None => fail("should not be none")
      case Some(a1) => a1.delete
    }

    assertTrue(Artist.findByName(artist.name).isEmpty)
  }


}