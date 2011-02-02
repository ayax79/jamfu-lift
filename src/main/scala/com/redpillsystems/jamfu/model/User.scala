package com.redpillsystems.jamfu.model

import com.google.appengine.api.datastore.Key
import org.joda.time.DateTime
import PersistenceHelper._
import javax.jdo.annotations._
import java.util.Date

@PersistenceCapable
class User(@PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) val key: Key,
           @Index @Persistent var username: String,
           @Persistent var firstName: String,
           @Persistent var lastName: String,
           @Persistent var email: String,
           @Persistent var created: Date,
           @Persistent var modified: Date) extends JDOModelObject {

  def this(username: String, firstName: String, lastName: String, email: String) =
    this(null, username, firstName, lastName, email, new Date, new Date)


}

object User {

  def findByUsername(username: String): Option[User] =
    queryFirst("select from " + classOf[User].getName + " where username = :username", Map(":username" -> username))


}