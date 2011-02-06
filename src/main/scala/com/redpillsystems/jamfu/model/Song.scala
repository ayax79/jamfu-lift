package com.redpillsystems.jamfu.model

import javax.jdo.annotations.{IdentityType, PersistenceCapable, Persistent}

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class Song extends JDOModel {

  @Persistent var artist: Artist = _
  @Persistent var name: String = _
  @Persistent var url: String = _
  @Persistent var votes: Long = 0L

}

object Song extends JDOModelObject