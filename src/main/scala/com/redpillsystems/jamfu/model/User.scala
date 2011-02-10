package com.redpillsystems.jamfu.model

import PersistenceHelper._
import com.google.appengine.api.datastore.Key
import javax.jdo.annotations._
import net.liftweb.common.Box

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class User extends JDOModel {


  @Persistent var username: String = _
  @Persistent var firstName: String = _
  @Persistent var lastName: String = _
  @Persistent var email: String = _

  def this(_username: String, _firstName: String, _lastname: String, _email: String) = {
    this ()
    username = _username
    firstName = _firstName
    lastName = _lastname
    email = _email
  }

  override protected def validate =
    List(
      username match {
        case null | "" => RequiredError("username")
        case _ => null
      },
      email match {
        case null | "" => RequiredError("email")
        case _ => null
      }).filter(_ != null)
}

object User {

  def findByUsername(username: String): Box[User] =
    find("select from " + classOf[User].getName + " where username == :username", Map("username" -> username))

  def findByKey(key: Key) = PersistenceHelper.findByKey(classOf[User], key)



}