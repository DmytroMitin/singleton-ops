package singleton.ops

import macrocompat.bundle
import scala.reflect.macros.whitebox
import singleton.ops.impl._

trait Plus[T, A, B] extends Op {
  override type Out <: T
}

object Plus {
  implicit def materializePlus[T, A <: T, B <: T](
      implicit nt: Numeric[T]
  ): Plus[T, A, B] = macro PlusMacro.materialize[T, A, B]

  @bundle
  final class PlusMacro(val c: whitebox.Context) extends Macros {
    def materialize[T: c.WeakTypeTag, A: c.WeakTypeTag, B: c.WeakTypeTag](
        nt: c.Expr[Numeric[T]]
    ): c.Tree =
      materializeOp2Gen[Plus, T, A, B].usingFunction(evalTyped(nt).plus)
  }
}
