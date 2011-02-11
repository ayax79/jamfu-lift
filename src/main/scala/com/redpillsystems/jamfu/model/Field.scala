package com.redpillsystems.jamfu.model

import net.liftweb.util.FieldError
import collection.mutable.ListBuffer

abstract class Field[FieldType <: Any, OwnerType <: JDOModel[OwnerType]](val fieldOwner: OwnerType) {
  fieldOwner.addField(this)

  def is: FieldType = load

  def apply(fv: FieldType):OwnerType = {
    set(fv)
    fieldOwner
  }


  protected def set(in: FieldType): Unit

  protected def load: FieldType

  def validations: List[FieldType => List[FieldError]] = Nil

  def validate: List[FieldError] = {
    val cv = is
    val errorRet: ListBuffer[FieldError] = new ListBuffer

    /*
     validations.flatMap{
     case pf: PartialFunction[FieldType, List[FieldError]] =>
     if (pf.isDefinedAt(cv)) pf(cv)
     else Nil
     case f => f(cv)
     }
     */

    def runValidations(validators: List[FieldType => List[FieldError]]) {
      validators match {
        case Nil => ()
        case x :: rest =>
          val errors = x match {
            case pf: PartialFunction[FieldType, List[FieldError]] =>
              if (pf.isDefinedAt(cv)) pf(cv)
              else Nil
            case f => f(cv)
          }

          (errors, x) match {
            case (Nil, _) => runValidations(rest)
            case (errors, e: StopValidationOnError[FieldType]) => errorRet.appendAll(errors)
            case (errors, _) => errorRet.appendAll(errors)
            runValidations(rest)
          }
      }
    }
    runValidations(validations)
    errorRet.toList
  }


}

trait StopValidationOnError[T] extends Function1[T, List[FieldError]]