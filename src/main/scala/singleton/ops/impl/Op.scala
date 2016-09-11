package singleton.ops.impl

import macrocompat.bundle
import scala.reflect.macros.whitebox
import singleton.ops.impl._

trait SingletonTypeExpr extends Serializable {
  type BaseType
  type Out <: BaseType with Singleton
  type OutInt <: Int with Singleton
  type OutLong <: Long with Singleton
  type OutDouble <: Double with Singleton
  type OutString <: String with Singleton
  val value : Out {}
  //For debugging only
  def outTypeName : String = {
    if (value.isInstanceOf[Int]) "Int"
    else "Unknown"
  }
//  match {
//    case (p.isInstanceOf[Int]) => "Int"
//    case (_ : Long with Singleton) => "Long"
//    case (_ : Double with Singleton) => "Double"
//    case (_ : String with Singleton) => "String"
//    case _ => "Unknown"
//  }
}


trait SingletonTypeExprBase[B] extends SingletonTypeExpr {type BaseType = B}

trait SingletonTypeExprInt extends SingletonTypeExprBase[Int] {type OutInt = Out}
trait SingletonTypeExprLong extends SingletonTypeExprBase[Long] {type OutLong = Out}
trait SingletonTypeExprDouble extends SingletonTypeExprBase[Double] {type OutDouble = Out}
trait SingletonTypeExprString extends SingletonTypeExprBase[String] {type OutString = Out}


trait SingletonTypeValue[S <: Singleton] extends SingletonTypeExpr

sealed trait SingletonTypeValueInt[S <: Int with Singleton] extends SingletonTypeValue[S] with SingletonTypeExprInt {type Out = S}
sealed trait SingletonTypeValueLong[S <: Long with Singleton] extends SingletonTypeValue[S] with SingletonTypeExprLong {type Out = S}
sealed trait SingletonTypeValueDouble[S <: Double with Singleton] extends SingletonTypeValue[S] with SingletonTypeExprDouble {type Out = S}
sealed trait SingletonTypeValueString[S <: String with Singleton] extends SingletonTypeValue[S] with SingletonTypeExprString {type Out = S}


object SingletonTypeValue {
  implicit def implInt[S <: Int with Singleton]
  (implicit v: ValueOf[S]) : SingletonTypeValueInt[S] =
    new SingletonTypeValueInt[S] {val value : Out = valueOf[S]}

  implicit def implLong[S <: Long with Singleton]
  (implicit v: ValueOf[S], di : DummyImplicit) : SingletonTypeValueLong[S] =
    new SingletonTypeValueLong[S] {val value : Out = valueOf[S]}

  implicit def implDouble[S <: Double with Singleton]
  (implicit v: ValueOf[S], di : DummyImplicit, di2 : DummyImplicit) : SingletonTypeValueDouble[S] =
    new SingletonTypeValueDouble[S] {val value : Out = valueOf[S]}

  implicit def implString[S <: String with Singleton]
  (implicit v: ValueOf[S], di : DummyImplicit, di2 : DummyImplicit, di3 : DummyImplicit) : SingletonTypeValueString[S] =
    new SingletonTypeValueString[S] {val value : Out = valueOf[S]}
}



trait Op {
  type Out
  val value: Out {}
}

trait Op2[B, T1, S1 <: T1 with Singleton, T2, S2 <: T2 with Singleton] extends SingletonTypeExpr {
  type BaseType = B
}

trait SumMacro[B, T1, S1 <: T1 with Singleton, T2, S2 <: T2 with Singleton] extends Op2[B,T1,S1,T2,S2]

@bundle
object SumMacro {
  implicit def call[B, T1, S1 <: T1 with Singleton, T2, S2 <: T2 with Singleton]
  (implicit nt1: Numeric[T1], nt2: Numeric[T2]): SumMacro[B,T1,S1,T2,S2] =
    macro Macro.impl[B,T1,S1,T2,S2]

  final class Macro(val c: whitebox.Context) extends Macros {
    def impl[
      B: c.WeakTypeTag,
      T1: c.WeakTypeTag,
      S1 <: T1 with Singleton: c.WeakTypeTag,
      T2: c.WeakTypeTag,
      S2 <: T2 with Singleton: c.WeakTypeTag
    ](nt1: c.Expr[Numeric[T1]], nt2: c.Expr[Numeric[T2]]): c.Tree =
      materializeOp3[SumMacro[_,_, _, _, _], B,T1,S1,T2,S2]
        .usingFunction(evalTyped(nt1).plus)
  }
}
