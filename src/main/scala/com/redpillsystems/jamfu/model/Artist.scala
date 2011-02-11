package com.redpillsystems.jamfu.model

import java.util.{Set => JSet, List => JList}
import com.google.appengine.api.datastore.Key
import scala.collection.JavaConversions._
import PersistenceHelper._
import javax.jdo.annotations.{IdentityType, PersistenceCapable, Persistent}

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class Artist extends JDOModel[Artist] {

  @Persistent var name: String = _

  @Persistent var memberKeys: JSet[Key] = _

  @Persistent(mappedBy = "artist") var songs: JList[Song] = _

  def this(name: String, members: Set[User]) = {
    this ()
    this.name = name
    this.memberKeys = members.map(_.key.is)
  }

}

object Artist {

  protected val className = classOf[Artist].getName

  def findByName(nm: String): Option[Artist] = find("select from " + className + " where name == :name", Map("name" -> nm))


  /**
   * Finds an artist by one of it's members
   * @param u The member to findAll the artist by
   */
  def findByMember(u: User): Option[Artist] = find("select from " + className + " where memberKeys == :key", Map("key" -> u.key.is))


  def findByKey(key: Key) = PersistenceHelper.findByKey(classOf[Artist], key)

}