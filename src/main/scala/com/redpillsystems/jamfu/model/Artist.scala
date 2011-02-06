package com.redpillsystems.jamfu.model

import java.util.{Set => JSet, List => JList}
import com.google.appengine.api.datastore.Key
import scala.collection.JavaConversions._
import PersistenceHelper._
import javax.jdo.annotations.{IdentityType, PersistenceCapable, Persistent}

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class Artist extends JDOModel {

  @Persistent var name: String = _

  @Persistent var memberKeys: JSet[Key] = _

  @Persistent(mappedBy = "artist") var songs: JList[Song] = _

  def this(name: String, members: Set[User]) = {
    this ()
    this.name = name
    this.memberKeys = members.map(_.key)
  }

}

object Artist extends JDOModelObject {

  protected val className = classOf[Artist].getName

  def findByName(nm: String): Option[Artist] = queryFirst("select from " + className + " where name == :name", Map("name" -> nm))


  /**
   * Finds an artist by one of it's members
   * @param u The member to find the artist by
   */
  def findByMember(u: User): Option[Artist] = queryFirst("select from " + className + " where memberKeys == :key", Map("key" -> u.key))

}