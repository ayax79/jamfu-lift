package com.redpillsystems.jamfu.model

import collection.JavaConversions._
import java.util.{List => JList}
import collection.JavaConversions
import javax.jdo.{Transaction, PersistenceManager, JDOHelper, PersistenceManagerFactory}

object PersistenceHelper {

  @volatile var instance: PersistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  def query[T](query: String, params: AnyRef = null): List[T] = perform(ph => ph.query(query, params))

  def queryFirst[T](q: String, params: AnyRef = null): Option[T] = perform(ph => ph.queryFirst(q, params))


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
  def query[T](query: String, params: AnyRef = null): List[T] = {
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

  def queryFirst[T](q: String, params: AnyRef = null): Option[T] = query(q, params) match {
    case Nil => None
    case head :: _ => Some(head)
  }

  def delete(ar: JDOModel) = {
    val ar2 = pm.getObjectById(ar.getClass, ar.key)
    pm.deletePersistent(ar2)
  }

  def save(ar: AnyRef):Unit = pm.makePersistent(ar)

}

