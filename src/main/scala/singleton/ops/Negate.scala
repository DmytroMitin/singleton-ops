package singleton.ops

import macrocompat.bundle
import scala.reflect.macros.whitebox
import singleton.ops.impl._

trait Negate[A] extends Op

object Negate extends Op1Companion[Negate] {

  implicit def materializeNegate[T, A <: T](
      implicit nt: Numeric[T]
  ): Negate[A] = macro NegateMacro.materialize[T, A]

  @bundle
  final class NegateMacro(val c: whitebox.Context) extends Macros {
    def materialize[T, A: c.WeakTypeTag](
        nt: c.Expr[Numeric[T]]
    ): c.Tree =
      materializeUnaryOp[Negate, A].apply(evalTyped(nt).negate)
  }
}
