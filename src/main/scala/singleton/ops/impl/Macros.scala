package singleton.ops.impl

import macrocompat.bundle
import scala.reflect.macros.whitebox

@ bundle
trait Macros {
  val c: whitebox.Context

  import c.universe._

  def abort(msg: String): Nothing =
    c.abort(c.enclosingPosition, msg)

  def constantTypeOf[T](t: T): Type =
    c.internal.constantType(Constant(t))

  def constantTypeAndValueOf[T](t: T): (Type, Tree) =
    (constantTypeOf(t), Literal(Constant(t)))

  def extractSingletonValue[T](tpe: Type): T = {
    def extractionFailed(tpe: Type) =
      abort(s"Cannot extract value from ${tpe.typeSymbol.fullName}")

    val value = tpe match {
      case ConstantType(Constant(t)) => t
      case TypeRef(_, sym, _) =>
        sym.info match {
          case ConstantType(Constant(t)) => t
          case otherTpe => extractionFailed(otherTpe)
        }
      case otherTpe => extractionFailed(otherTpe)
    }

    value.asInstanceOf[T]
  }

  def evalTyped[T](expr: c.Expr[T]): T =
    c.eval(c.Expr[T](c.untypecheck(expr.tree)))

  def materializeOp1[F[_], A](
      implicit ev1: c.WeakTypeTag[F[_]],
      ev2: c.WeakTypeTag[A]
  ): MaterializeOp1Aux =
    new MaterializeOp1Aux(symbolOf[F[_]], weakTypeOf[A])

  def materializeOp1Gen[F[_, _], T, A](
      implicit ev1: c.WeakTypeTag[F[_, _]],
      ev2: c.WeakTypeTag[T],
      ev3: c.WeakTypeTag[A]
  ): MaterializeOp1AuxGen =
    new MaterializeOp1AuxGen(symbolOf[F[_, _]], weakTypeOf[T], weakTypeOf[A])

  final class MaterializeOp1Aux(opSym: TypeSymbol, aTpe: Type) {
    def usingFunction[T, R](f: T => R): Tree =
      mkOp1Tree(computeOutValue(f))

    private def computeOutValue[T, R](f: T => R): R =
      f(extractSingletonValue[T](aTpe))

    private def mkOp1Tree[T](outValue: T): Tree =
      mkOpTree(tq"$opSym[$aTpe]", outValue)
  }

  final class MaterializeOp1AuxGen(opSym: TypeSymbol, tTpe: Type, aTpe: Type) {
    def usingFunction[T1, R](f: T1 => R): Tree =
      mkOp1Tree(computeOutValue(f))

    private def computeOutValue[T1, R](f: T1 => R): R =
      f(extractSingletonValue[T1](aTpe))

    private def mkOp1Tree[T1](outValue: T1): Tree =
      mkOpTree(tq"$opSym[$tTpe, $aTpe]", outValue)
  }

  def materializeOp2[F[_, _], A, B](
      implicit ev1: c.WeakTypeTag[F[_, _]],
      ev2: c.WeakTypeTag[A],
      ev3: c.WeakTypeTag[B]
  ): MaterializeOp2Aux =
    new MaterializeOp2Aux(symbolOf[F[_, _]], weakTypeOf[A], weakTypeOf[B])

  def materializeOp2Gen[F[_, _, _], T, A, B](
      implicit ev1: c.WeakTypeTag[F[_, _, _]],
      ev2: c.WeakTypeTag[T],
      ev3: c.WeakTypeTag[A],
      ev4: c.WeakTypeTag[B]
  ): MaterializeOp2AuxGen =
    new MaterializeOp2AuxGen(symbolOf[F[_, _, _]],
                             weakTypeOf[T],
                             weakTypeOf[A],
                             weakTypeOf[B])

  final class MaterializeOp2Aux(opSym: TypeSymbol, aTpe: Type, bTpe: Type) {
    def usingFunction[T1, T2, R](f: (T1, T2) => R): Tree =
      mkOp2Tree(computeOutValue(f))

    def usingPredicate[T1](f: (T1, T1) => Boolean): Tree = {
      val outValue = computeOutValue(f)
      if (outValue) {
        mkOp2Tree(outValue)
      } else {
        abort(s"Cannot prove ${opSym.name}[${show(aTpe)}, ${show(bTpe)}]")
      }
    }

    private def computeOutValue[T1, T2, R](f: (T1, T2) => R): R = {
      val aValue = extractSingletonValue[T1](aTpe)
      val bValue = extractSingletonValue[T2](bTpe)
      f(aValue, bValue)
    }

    private def mkOp2Tree[T1](outValue: T1): Tree =
      mkOpTree(tq"$opSym[$aTpe, $bTpe]", outValue)
  }

  final class MaterializeOp2AuxGen(opSym: TypeSymbol,
                                   tTpe: Type,
                                   aTpe: Type,
                                   bTpe: Type) {
    def usingFunction[T1, T2, R](f: (T1, T2) => R): Tree =
      mkOp2Tree(computeOutValue(f))

    def usingPredicate[T](f: (T, T) => Boolean): Tree = {
      val outValue = computeOutValue(f)
      if (outValue) {
        mkOp2Tree(outValue)
      } else {
        abort(
          s"Cannot prove ${opSym.name}[${show(tTpe)}, ${show(aTpe)}, ${show(bTpe)}]")
      }
    }

    private def computeOutValue[T1, T2, R](f: (T1, T2) => R): R = {
      val aValue = extractSingletonValue[T1](aTpe)
      val bValue = extractSingletonValue[T2](bTpe)
      f(aValue, bValue)
    }

    private def mkOp2Tree[T](outValue: T): Tree =
      mkOpTree(tq"$opSym[$tTpe, $aTpe, $bTpe]", outValue)
  }

  def mkOpTree[T](appliedTpe: Tree, outValue: T): Tree = {
    val (outTpe, outTree) = constantTypeAndValueOf(outValue)
    q"""
      new $appliedTpe {
        type Out = $outTpe
        val value: $outTpe = $outTree
      }
    """
  }
}
