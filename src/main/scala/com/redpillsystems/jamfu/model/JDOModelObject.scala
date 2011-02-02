package com.redpillsystems.jamfu.model

import javax.jdo.annotations.{IdGeneratorStrategy, PrimaryKey, Persistent}
import com.google.appengine.api.datastore.Key
import java.util.Date

trait JDOModelObject[T] {

  @PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) var key: Key = _

  @Persistent var created = new Date
  @Persistent var modified = new Date

  def save:T = {
    modified = new Date
    PersistenceHelper.save(this.asInstanceOf[T])
  }

  def delete:Unit = PersistenceHelper.delete(this)

}