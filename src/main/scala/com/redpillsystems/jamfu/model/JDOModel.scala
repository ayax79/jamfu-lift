package com.redpillsystems.jamfu.model

import javax.jdo.annotations.{IdGeneratorStrategy, PrimaryKey, Persistent}
import com.google.appengine.api.datastore.Key
import java.util.Date

trait JDOModel {

  @PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) var key: Key = _

  @Persistent var created = new Date
  @Persistent var modified = new Date

  final def save(ph: PersistenceHelper): List[ValidationError] = {
    modified = new Date
    validate match {
      case Nil =>
        ph.save(this)
        Nil
      case errors@_ => errors
    }
  }

  final def delete(ph: PersistenceHelper): Unit = ph.delete(this)

  protected def validate: List[ValidationError] = Nil

}

abstract class ValidationError(msg: String)
case class RequiredError(msg: String) extends ValidationError(msg)

