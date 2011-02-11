package com.redpillsystems.jamfu.model

import junit.framework.Assert._
import org.junit.Test

class ArtistTest extends GoogleTest {

  @Test
  def testCrud:Unit = {
    val u = new User("artisttestuser", "ArtistUserFirst", "ArtistTestUserLast", "artist@sdflkj.com")
    u.save
    assertNotNull(u.key.is)
    assertTrue(User.findByKey(u.key.is).isDefined)

    // todo fix

//    def artist = new Artist("testartist", Set(u))
//    assertEquals(1, artist.memberKeys.size)
//
//    assertFalse(Artist.findByMember(u).isEmpty)
//
//    Artist.findByName(artist.name) match {
//      case None => fail("should not be none")
//      case Some(a1) => a1.delete_!
//    }
//
//    assertTrue(Artist.findByName(artist.name).isEmpty)
  }


}