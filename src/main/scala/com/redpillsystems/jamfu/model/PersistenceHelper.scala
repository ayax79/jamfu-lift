package com.redpillsystems.jamfu.model

import collection.JavaConversions._
import java.util.{List => JList}
import collection.JavaConversions
import javax.jdo.{Transaction, PersistenceManager, JDOHelper, PersistenceManagerFactory}

protected[model] object PersistenceHelper {

  lazy val instance: PersistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  def save[T](ar: T):T = perform((pm: PersistenceManager) => pm.makePersistent(ar))

  def query[T](query: String, params: AnyRef = null): List[T] = perform{
    pm: PersistenceManager =>
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

  def delete[T](ar: JDOModelObject[T]) = perform{
    pm: PersistenceManager =>
      val ar2 = pm.getObjectById(ar.getClass, ar.key)
      pm.deletePersistent(ar2)
  }


  def perform[T](func: (PersistenceManager => T)): T = {
    val pm: PersistenceManager = instance.getPersistenceManager
    try {
      func(pm)
    } finally {
      pm.close
    }
  }

}