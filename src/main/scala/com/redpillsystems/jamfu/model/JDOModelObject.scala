package com.redpillsystems.jamfu.model

import javax.jdo.PersistenceManager

trait JDOModelObject {

  def save = PersistenceHelper.save(this)

  def delete = PersistenceHelper.delete(this)

}