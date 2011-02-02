package com.redpillsystems.jamfu.model

import com.google.appengine.api.datastore.Key
import org.joda.time.DateTime
import PersistenceHelper._
import javax.jdo.annotations._
import java.util.Date


@PersistenceCapable(identityType = IdentityType.APPLICATION)
class User extends JDOModelObject {

  @PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) var key: Key = _

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

object User {

  def findByUsername(username: String): Option[User] =
    queryFirst("select from " + classOf[User].getName + " where username = :username", Map(":username" -> username))


}