package com.redpillsystems.jamfu.model

import collection.JavaConversions._
import java.util.{List => JList}
import collection.JavaConversions
import javax.jdo.{Transaction, PersistenceManager, JDOHelper, PersistenceManagerFactory}
import com.google.appengine.api.datastore.Key
import net.liftweb.common.{Full, Empty, Failure, Box}

object PersistenceHelper {

  @volatile var instance: PersistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  def findAll[T](query: String, params: AnyRef = null): List[T] = perform(ph => ph.findAll(query, params))

  def find[T](q: String, params: AnyRef = null): Box[T] = perform(ph => ph.find(q, params))

  def findByKey[T <: JDOModel[T]](cl: Class[T], key: Key):Box[T] = perform {
    ph => ph.pm.getObjectById(cl, key) match {
      case null => None
      case s@_ => Some(s)
    }
  }

  def perform[T](func: (PersistenceHelper => T)): T = {
    val pm: PersistenceManager = instance.getPersistenceManager
    val tx: Transaction = pm.currentTransaction
    tx.begin
    try {
      val result: T = func(new PersistenceHelper(pm))
      tx.commit
      result
    }
    catch {
      case e: Exception =>
        tx.rollback
        throw e
    }
    finally {
      pm.close
    }
  }
}

class PersistenceHelper(val pm: PersistenceManager) {
  def findAll[T](query: String, params: AnyRef = null): List[T] = {
    val q = pm.newQuery(query)
    val result: JList[T] = params match {
      case map: Map[_, _] => q.executeWithMap(map).asInstanceOf[JList[T]]
      case _ => q.execute.asInstanceOf[JList[T]]
    }

    JavaConversions.asScalaIterable(result).toList match {
      case null => Nil
      case s@_ => s
    }
  }

  def find[T](q: String, params: AnyRef = null): Option[T] = findAll(q, params) match {
    case Nil => None
    case head :: _ => Full(head)
  }

  def delete(ar: JDOModel[_]) = {
    val ar2 = pm.getObjectById(ar.getClass, ar.key.is)
    pm.deletePersistent(ar2)
  }

  def save(ar: AnyRef):Unit = pm.makePersistent(ar)

}

