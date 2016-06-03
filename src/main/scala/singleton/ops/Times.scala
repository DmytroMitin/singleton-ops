package singleton.ops

import macrocompat.bundle
import scala.reflect.macros.whitebox
import singleton.ops.macros.MacroUtils

trait Times[A, B] extends Op

object Times {
  type Aux[A, B, Out0] = Times[A, B] { type Out = Out0 }

  def apply[A, B](implicit ev: Times[A, B]): Aux[A, B, ev.Out] = ev

  implicit def materializeTimes[T, A <: T, B <: T](
      implicit nt: Numeric[T]
  ): Times[A, B] = macro TimesMacro.materialize[T, A, B]

  @bundle
  final class TimesMacro(val c: whitebox.Context) extends MacroUtils {
    def materialize[T, A: c.WeakTypeTag, B: c.WeakTypeTag](
        nt: c.Expr[Numeric[T]]
    ): c.Tree =
      materializeBinaryOp[Times, A, B].apply(evalTyped(nt).times)
  }
}
