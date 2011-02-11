package com.redpillsystems.jamfu.model

import javax.jdo.annotations.{IdGeneratorStrategy, PrimaryKey, Persistent}
import com.google.appengine.api.datastore.Key
import java.util.Date
import net.liftweb.util.FieldError
import org.joda.time.{DateTime}

trait JDOModel[ModelType <: JDOModel[ModelType]] {

  @PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY, name = "key") protected var _key: Key = _

  @Persistent protected var _created = new Date
  @Persistent protected var _modified = new Date

  private var mappedFieldList: List[Field[Any, ModelType]] = Nil;

  object key extends Field[Key, ModelType](this.asInstanceOf[ModelType]) {
    protected def load = _key

    protected def set(in: Key) = {
      _key = in
    }
  }

  object created extends Field[DateTime, ModelType](this.asInstanceOf[ModelType]) {
    protected def load = new DateTime(_created)

    protected def set(in: DateTime) = {
      _created = in.toDate
    }
  }

  object modified extends Field[DateTime, ModelType](this.asInstanceOf[ModelType]) {
    protected def load = new DateTime(_modified)

    protected def set(in: DateTime) = {
      _modified = in.toDate
    }
  }


  private[model] def addField[FieldType <: Any](field: Field[FieldType, ModelType]) {
    mappedFieldList = field.asInstanceOf[Field[Any, ModelType]] :: mappedFieldList
  }


  final def save: Boolean = {
    PersistenceHelper.perform(_.save(this))
    true
  }

  final def delete_! : Boolean = {
    PersistenceHelper.perform(_.delete(this))
    true
  }

  def validate: List[FieldError] = mappedFieldList.flatMap(_.validate)


}

