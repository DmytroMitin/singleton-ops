package singleton.ops.impl

import macrocompat.bundle

import scala.reflect.macros.whitebox

/********************************************************************************************************
  * Sum
  *******************************************************************************************************/
trait SumMacro[T1, S1 <: T1 with Singleton, T2, S2 <: T2 with Singleton]
    extends Op2[T1, S1, T2, S2]

@bundle
object SumMacro {
  implicit def call[T1, S1 <: T1 with Singleton, T2, S2 <: T2 with Singleton]:
  SumMacro[T1, S1, T2, S2] = macro Macro.impl[T1, S1, T2, S2]

  final class Macro(val c: whitebox.Context) extends GeneralMacros {
    def impl[
        T1: c.WeakTypeTag,
        S1 <: T1 with Singleton: c.WeakTypeTag,
        T2: c.WeakTypeTag,
        S2 <: T2 with Singleton: c.WeakTypeTag
    ] : c.Tree =
      materializeOp2Gen[SumMacro[_, _, _, _], T1, S1, T2, S2]
          .usingFuncName("Plus")
  }
}

/*******************************************************************************************************/
/********************************************************************************************************
  * ToLong
  *******************************************************************************************************/
trait ToLongMacro[B, T1, S1 <: T1 with Singleton] extends Op1[B, T1, S1]

@bundle
object ToLongMacro {
  implicit def call[B, T1, S1 <: T1 with Singleton](
      implicit nt1: Numeric[T1]): ToLongMacro[B, T1, S1] =
    macro Macro.impl[B, T1, S1]

  final class Macro(val c: whitebox.Context) extends GeneralMacros {
    def impl[
        B: c.WeakTypeTag,
        T1: c.WeakTypeTag,
        S1 <: T1 with Singleton: c.WeakTypeTag
    ](nt1: c.Expr[Numeric[T1]]): c.Tree =
      materializeOp1Gen[ToLongMacro[_, _, _], B, T1, S1]
        .usingFunction(evalTyped(nt1).toLong)
  }
}

/*******************************************************************************************************/
/********************************************************************************************************
  * ToInt
  *******************************************************************************************************/
trait ToIntMacro[B, T1, S1 <: T1 with Singleton] extends Op1[B, T1, S1]

@bundle
object ToIntMacro {
  implicit def call[B, T1, S1 <: T1 with Singleton](
      implicit nt1: Numeric[T1]): ToIntMacro[B, T1, S1] =
    macro Macro.impl[B, T1, S1]

  final class Macro(val c: whitebox.Context) extends GeneralMacros {
    def impl[
        B: c.WeakTypeTag,
        T1: c.WeakTypeTag,
        S1 <: T1 with Singleton: c.WeakTypeTag
    ](nt1: c.Expr[Numeric[T1]]): c.Tree =
      materializeOp1Gen[ToIntMacro[_, _, _], B, T1, S1]
        .usingFunction(evalTyped(nt1).toInt)
  }
}
/*******************************************************************************************************/
