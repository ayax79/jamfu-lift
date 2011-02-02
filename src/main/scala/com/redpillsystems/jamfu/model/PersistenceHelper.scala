package com.redpillsystems.jamfu.model

import collection.JavaConversions._
import javax.jdo.{PersistenceManager, JDOHelper, PersistenceManagerFactory}
import java.util.{List => JList}
import collection.JavaConversions

object PersistenceHelper {

  lazy val instance: PersistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  def save(ar: AnyRef) = perform((pm: PersistenceManager) => pm.makePersistent(ar))


  def query[T](query: String, params:AnyRef = null): List[T] = perform {
    pm: PersistenceManager =>
      val q = pm.newQuery(query)
      val result:JList[T] = params match {
        case map:Map[_,_] => q.executeWithMap(map).asInstanceOf[JList[T]]
        case _ => q.execute.asInstanceOf[JList[T]]
      }

      JavaConversions.asScalaIterable(result).toList match {
        case null => Nil
        case s@_ => s
      }
  }

  def queryFirst[T](q: String, params:AnyRef): Option[T] = query(q, params) match {
    case Nil => None
    case head::_ => Some(head)
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