package com.redpillsystems.jamfu.model

import PersistenceHelper._
import com.google.appengine.api.datastore.Key
import javax.jdo.annotations._
import java.lang.String
import net.liftweb.http.{CleanRequestVarOnSessionTransition, RequestVar, SessionVar}
import net.liftweb.common.{Full, Empty, Box}

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class User extends JDOModel[User] {

  object username extends Field[String, User](this) {
    protected def load = _username

    protected def set(in: String) = {
      _username = in
    }
  }

  object firstName extends Field[String, User](this) {
    protected def load = _firstName

    protected def set(in: String) = {
      _firstName = in
    }
  }

  object lastName extends Field[String, User](this) {
    protected def load = _lastName

    protected def set(in: String) = {
      _lastName = in
    }
  }

  object email extends Field[String, User](this) {
    protected def set(in: String) = {
      _email = in
    }

    protected def load = _email
  }

  object fbid extends Field[Long, User](this) {
    protected def load = _fbid

    protected def set(in: Long) = {
      _fbid = in
    }
  }


  @Persistent protected var _username: String = _
  @Persistent protected var _firstName: String = _
  @Persistent protected var _lastName: String = _
  @Persistent protected var _email: String = _
  @Persistent protected var _fbid: Long = -1

  def this(_username: String, _firstName: String, _lastname: String, _email: String) = {
    this ()
    this._username = _username
    this._firstName = _firstName
    this._lastName = _lastname
    this._email = _email
  }

}

object User {
  val className: String = classOf[User].getName


  def findByUsername(username: String): Box[User] =
    find("select from " + className + " where _username == :username", Map("username" -> username))

  def findByKey(key: Key) = PersistenceHelper.findByKey(classOf[User], key)

  def findByFbId(fbid: Long): Box[User] = find("select from " + className + " where fbid == :fbid", Map("fbid" -> fbid))

  def findByFbId(fbid: String): Box[User] = findByFbId(fbid.toLong)

  private object curUserKey extends SessionVar[Box[Key]](Empty)

  def currentUserKey: Box[Key] = curUserKey.is

  private object curUser extends RequestVar[Box[User]](currentUserKey.flatMap(key => findByKey(key))) with CleanRequestVarOnSessionTransition

  def currentUser: Box[User] = curUser.is

  def logUserIn(who:User) = {
    curUserKey.remove()
    curUser.remove()
    curUserKey(Full(who.key.is))
  }


}