package com.redpillsystems.jamfu.model

import javax.jdo.PersistenceManager
import javax.jdo.annotations.{IdGeneratorStrategy, PrimaryKey, Persistent}
import com.google.appengine.api.datastore.Key
import org.joda.time.DateTime
import java.util.Date

trait JDOModelObject {

  @PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) var key: Key = _

  @Persistent var created = new Date
  @Persistent var modified = new Date

  def save = {
    modified = new Date
    PersistenceHelper.save(this)
  }

  def delete = PersistenceHelper.delete(this)

}