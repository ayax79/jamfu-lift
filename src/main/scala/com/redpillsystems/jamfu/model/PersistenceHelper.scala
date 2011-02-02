package com.redpillsystems.jamfu.model

import collection.JavaConversions._
import javax.jdo.{PersistenceManager, JDOHelper, PersistenceManagerFactory}

object PersistenceHelper {

  lazy val instance: PersistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  def save(ar: AnyRef) = perform((pm: PersistenceManager) => pm.makePersistent(ar))


  def query[T](query: String, params:AnyRef = null): Option[List[T]] = perform {
    pm: PersistenceManager =>
      val q = pm.newQuery(query)
      val result = params match {
        case map:Map[_,_] => q.executeWithMap(map)
        case _ => q.execute()
      }

      result.asInstanceOf[List[T]] match {
        case null => None
        case Nil => None
        case s@_ => Some(s)
      }
  }

  def queryFirst[T](q: String, params:AnyRef): Option[T] = query(q, params) match {
    case Some(s: List[T]) => Some(s.last)
    case _ => None
  }

  def delete(ar: AnyRef) = perform((pm: PersistenceManager) => pm.deletePersistent(ar))


  def perform[T](func: (PersistenceManager => T)) = {
    val pm: PersistenceManager = instance.getPersistenceManager
    try {
      func(pm)
    } finally {
      pm.close
    }
  }

}