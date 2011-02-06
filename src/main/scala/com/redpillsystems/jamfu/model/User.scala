package com.redpillsystems.jamfu.model

import PersistenceHelper._
import javax.jdo.annotations._

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class User extends JDOModel {


  @Persistent var username: String = _
  @Persistent var firstName: String = _
  @Persistent var lastName: String = _
  @Persistent var email: String = _

  def this(_username: String, _firstName:String, _lastname:String, _email:String) = {
    this()
    username = _username
    firstName = _firstName
    lastName = _lastname
    email = _email
  }

}

object User extends JDOModelObject {

  def findByUsername(username: String): Option[User] =
    queryFirst("select from " + classOf[User].getName + " where username == :username", Map("username" -> username))


}