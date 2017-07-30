package singleton.twoface.impl

import singleton.ops._
import singleton.ops.impl._
import macrocompat.bundle
import scala.reflect.macros.whitebox

trait TwoFaceAny[Face, T <: Face] extends Any {
  def isLiteral(implicit rt : RunTime[T]) : scala.Boolean = !rt
  @inline def getValue : Face
  override def toString = getValue.toString
}

object TwoFaceAny {
  @inline implicit def fromTwoFaceUnsafe[Face, T <: Face](tf : TwoFaceAny[Face, T]) : Face = tf.getValue
  @inline implicit def fromTwoFaceSafe[Face, T <: Face with Singleton](tf : TwoFaceAny[Face, T])
                                                                      (implicit sc: ValueOf[T]) : T {} = valueOf[T]


  ////////////////////////////////////////////////////////////////////////////////
  // The code duplication in `Shell` can indeed be reduced.
  // However, the duplication was favored because it had caused less 'red' in
  // IntelliJ, when the `apply` in the `Shell` traits has a concrete return type.
  // Example red code without concrete return type:
  // def testme[L <: Int,R <: Int](l : TwoFace.Int[L], r : TwoFace.Int[R])
  //     (implicit tfs : TwoFace.Int.Shell2[+,L,Int,R,Int]) = tfs(l,r)
  // val tmres = testme(TwoFace.Int(2),testme(TwoFace.Int(2),TwoFace.Int(2)))
  ////////////////////////////////////////////////////////////////////////////////
  object Shell {
    object Two {
      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: scala.Char
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Char[Out]
      }
      @bundle
      object Char {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: scala.Int
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Int[Out]
      }
      @bundle
      object Int {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: scala.Long
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Long[Out]
      }
      @bundle
      object Long {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: scala.Float
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Float[Out]
      }
      @bundle
      object Float {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: scala.Double
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Double[Out]
      }
      @bundle
      object Double {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: java.lang.String
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.String[Out]
      }
      @bundle
      object String {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

      @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
      trait Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
        type Out <: scala.Boolean
        def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Boolean[Out]
      }
      @bundle
      object Boolean {
        implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
        Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
        macro Macro.shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]

        final class Macro(val c: whitebox.Context) extends GeneralMacros {
          def shell2[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
          (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
           arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
           arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
            Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell2()
        }
      }

    }
  }

//  @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//  trait Shell2[Face, FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//    type Out <: scala.Int
//    def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : Int[Out]
//  }

  trait Builder[TF[T <: Face], Face, Shl2[_,_,_,_,_,_]] {
    type Shell2[Func[_,_], Arg1, Arg1Wide, Arg2, Arg2Wide] =
      Shl2[Func[Arg1, Arg2], Func[Arg[1, Arg1], Arg[2, Arg2]], Arg1, Arg1Wide, Arg2, Arg2Wide]
    protected[singleton] def create[T <: Face](value : Face) : TF[T]
    implicit def apply[T <: Face with Singleton](value : T)(implicit tfb : Builder[TF, Face, Shl2])
    : TF[T] =  tfb.create[T](value)
    implicit def apply(value : Face)(implicit tfb : Builder[TF, Face, Shl2], di: DummyImplicit)
    : TF[Face] = tfb.create[Face](value)
    implicit def apply[T <: Face](implicit id : ValueOf[T], tfb : Builder[TF, Face, Shl2], di: DummyImplicit, di2 : DummyImplicit)
    : TF[T] = tfb.create[T](valueOf[T])
  }

  @bundle
  object Builder {
    final class Macro(val c: whitebox.Context) extends GeneralMacros {
      def unaryOp[OP](r : c.Tree)(implicit op : c.WeakTypeTag[OP]) : c.Tree =
        TwoFaceMaterializer[OP].unaryOp(r)
      def binOp[OP](l : c.Tree, r : c.Tree)(implicit op : c.WeakTypeTag[OP]) : c.Tree =
        TwoFaceMaterializer[OP].binOp(l, r)
      def triOp[OP](arg1 : c.Tree, arg2 : c.Tree, arg3 : c.Tree)(implicit op : c.WeakTypeTag[OP]) : c.Tree =
        TwoFaceMaterializer[OP].triOp(arg1, arg2, arg3)
      def prefixOp[OP](implicit op : c.WeakTypeTag[OP]) : c.Tree =
        TwoFaceMaterializer[OP].unaryOp(c.prefix.tree)
      def infixOp[OP](r : c.Tree)(implicit op : c.WeakTypeTag[OP]) : c.Tree =
        TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI1[OP](r : c.Tree)(
        di1 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI2[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI3[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI4[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
        di4 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI5[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
        di4 : c.Tree,
        di5 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI6[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
        di4 : c.Tree,
        di5 : c.Tree,
        di6 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI7[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
        di4 : c.Tree,
        di5 : c.Tree,
        di6 : c.Tree,
        di7 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI8[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
        di4 : c.Tree,
        di5 : c.Tree,
        di6 : c.Tree,
        di7 : c.Tree,
        di8 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
      def infixOpDI9[OP](r : c.Tree)(
        di1 : c.Tree,
        di2 : c.Tree,
        di3 : c.Tree,
        di4 : c.Tree,
        di5 : c.Tree,
        di6 : c.Tree,
        di7 : c.Tree,
        di8 : c.Tree,
        di9 : c.Tree,
      )(implicit op : c.WeakTypeTag[OP]) : c.Tree = TwoFaceMaterializer[OP].binOp(c.prefix.tree, r)
    }
  }

  trait Char[T <: scala.Char] extends Any with TwoFaceAny[scala.Char, T] {
//    def == [R <: XChar](r : R)(
//      implicit tfo : Boolean.Return[T == R]
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Char)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XInt](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Int)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XLong](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Long)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XFloat](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Float)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XDouble](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Double)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//      di9 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//
//    def +  [R](r : Char[R])(implicit tfo : Int.Return[T + R])        = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Char[R])(implicit tfo : Int.Return[T - R])        = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Char[R])(implicit tfo : Int.Return[T * R])        = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Char[R])(implicit tfo : Int.Return[T / R])        = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Char[R])(implicit tfo : Int.Return[T % R])        = tfo(this.getValue %  r.getValue)
//    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Int[R])(implicit tfo : Int.Return[T + R])         = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Int[R])(implicit tfo : Int.Return[T - R])         = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Int[R])(implicit tfo : Int.Return[T * R])         = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Int[R])(implicit tfo : Int.Return[T / R])         = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Int[R])(implicit tfo : Int.Return[T % R])         = tfo(this.getValue %  r.getValue)
//    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
//    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
//    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Long[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Long[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Long[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Long[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Long[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
//    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
//    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
//    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
//    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
//    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
//    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
//    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
//    def unary_-            (implicit tfo : Int.Return[Negate[T]])    = tfo(-this.getValue)
//    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
//    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
//    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
//    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
//    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
  }
  final class _Char[T <: scala.Char](val value : scala.Char) extends AnyVal with TwoFaceAny.Char[T] {
    @inline def getValue : scala.Char = value
  }
  implicit object Char extends TwoFaceAny.Builder[Char, scala.Char, Shell.Two.Char] {
    protected[singleton] def create[T <: scala.Char](value : scala.Char) = new _Char[T](value)
  }

  trait Int[T <: scala.Int] extends Any with TwoFaceAny[scala.Int, T] {
//    def == [R <: XChar](r : R)
//    : Boolean = macro Builder.Macro.infixOp[OpId.==]
//    def == (r : scala.Char)(
//      implicit
//      di1 : DummyImplicit
//    ) : Boolean =  macro Builder.Macro.infixOpDI1[OpId.==]
//    def == [R <: XInt](r : R)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit
//    ) : Boolean = macro Builder.Macro.infixOpDI2[OpId.==]
//    def == (r : scala.Int)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit
//    ) : Boolean = macro Builder.Macro.infixOpDI3[OpId.==]
//    def == [R <: XLong](r : R)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//    ) : Boolean = macro Builder.Macro.infixOpDI4[OpId.==]
//    def == (r : scala.Long)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//    ) : Boolean = macro Builder.Macro.infixOpDI5[OpId.==]
//    def == [R <: XFloat](r : R)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//    ) : Boolean = macro Builder.Macro.infixOpDI6[OpId.==]
//    def == (r : scala.Float)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//    ) : Boolean = macro Builder.Macro.infixOpDI7[OpId.==]
//    def == [R <: XDouble](r : R)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//    ) : Boolean = macro Builder.Macro.infixOpDI8[OpId.==]
//    def == (r : scala.Double)(
//      implicit
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//      di9 : DummyImplicit,
//    ) : Boolean = macro Builder.Macro.infixOpDI9[OpId.==]
//
//    def +  (r : Char) : Int = macro Builder.Macro.infixOp[OpId.+]
//    def +  (r : Int) : Int = macro Builder.Macro.infixOp[OpId.+]
//    def +  (r : Long) : Long = macro Builder.Macro.infixOp[OpId.+]
//    def +  (r : Float) : Float = macro Builder.Macro.infixOp[OpId.+]
//    def +  (r : Double) : Double = macro Builder.Macro.infixOp[OpId.+]
//    def -  (r : Char) : Int = macro Builder.Macro.infixOp[OpId.-]
//    def -  (r : Int) : Int = macro Builder.Macro.infixOp[OpId.-]
//    def -  (r : Long) : Long = macro Builder.Macro.infixOp[OpId.-]
//    def -  (r : Float) : Float = macro Builder.Macro.infixOp[OpId.-]
//    def -  (r : Double) : Double = macro Builder.Macro.infixOp[OpId.-]
//    def *  (r : Char) : Int = macro Builder.Macro.infixOp[OpId.*]
//    def *  (r : Int) : Int = macro Builder.Macro.infixOp[OpId.*]
//    def *  (r : Long) : Long = macro Builder.Macro.infixOp[OpId.*]
//    def *  (r : Float) : Float = macro Builder.Macro.infixOp[OpId.*]
//    def *  (r : Double) : Double = macro Builder.Macro.infixOp[OpId.*]
//    def /  (r : Char) : Int = macro Builder.Macro.infixOp[OpId./]
//    def /  (r : Int) : Int = macro Builder.Macro.infixOp[OpId./]
//    def /  (r : Long) : Long = macro Builder.Macro.infixOp[OpId./]
//    def /  (r : Float) : Float = macro Builder.Macro.infixOp[OpId./]
//    def /  (r : Double) : Double = macro Builder.Macro.infixOp[OpId./]
//    def %  (r : Char) : Int = macro Builder.Macro.infixOp[OpId.%]
//    def %  (r : Int) : Int = macro Builder.Macro.infixOp[OpId.%]
//    def %  (r : Long) : Long = macro Builder.Macro.infixOp[OpId.%]
//    def %  (r : Float) : Float = macro Builder.Macro.infixOp[OpId.%]
//    def %  (r : Double) : Double = macro Builder.Macro.infixOp[OpId.%]
//    def <  (r : Char) : Boolean = macro Builder.Macro.infixOp[OpId.<]
//    def <  (r : Int) : Boolean = macro Builder.Macro.infixOp[OpId.<]
//    def <  (r : Long) : Boolean = macro Builder.Macro.infixOp[OpId.<]
//    def <  (r : Float) : Boolean = macro Builder.Macro.infixOp[OpId.<]
//    def <  (r : Double) : Boolean = macro Builder.Macro.infixOp[OpId.<]
//    def >  (r : Char) : Boolean = macro Builder.Macro.infixOp[OpId.>]
//    def >  (r : Int) : Boolean = macro Builder.Macro.infixOp[OpId.>]
//    def >  (r : Long) : Boolean = macro Builder.Macro.infixOp[OpId.>]
//    def >  (r : Float) : Boolean = macro Builder.Macro.infixOp[OpId.>]
//    def >  (r : Double) : Boolean = macro Builder.Macro.infixOp[OpId.>]
//    def <= (r : Char) : Boolean = macro Builder.Macro.infixOp[OpId.<=]
//    def <= (r : Int) : Boolean = macro Builder.Macro.infixOp[OpId.<=]
//    def <= (r : Long) : Boolean = macro Builder.Macro.infixOp[OpId.<=]
//    def <= (r : Float) : Boolean = macro Builder.Macro.infixOp[OpId.<=]
//    def <= (r : Double) : Boolean = macro Builder.Macro.infixOp[OpId.<=]
//    def >= (r : Char) : Boolean = macro Builder.Macro.infixOp[OpId.>=]
//    def >= (r : Int) : Boolean = macro Builder.Macro.infixOp[OpId.>=]
//    def >= (r : Long) : Boolean = macro Builder.Macro.infixOp[OpId.>=]
//    def >= (r : Float) : Boolean = macro Builder.Macro.infixOp[OpId.>=]
//    def >= (r : Double) : Boolean = macro Builder.Macro.infixOp[OpId.>=]
//    def == (r : Char) : Boolean = macro Builder.Macro.infixOp[OpId.==]
//    def == (r : Int) : Boolean = macro Builder.Macro.infixOp[OpId.==]
//    def == (r : Long) : Boolean = macro Builder.Macro.infixOp[OpId.==]
//    def == (r : Float) : Boolean = macro Builder.Macro.infixOp[OpId.==]
//    def == (r : Double) : Boolean = macro Builder.Macro.infixOp[OpId.==]
//    def != (r : Char) : Boolean = macro Builder.Macro.infixOp[OpId.!=]
//    def != (r : Int) : Boolean = macro Builder.Macro.infixOp[OpId.!=]
//    def != (r : Long) : Boolean = macro Builder.Macro.infixOp[OpId.!=]
//    def != (r : Float) : Boolean = macro Builder.Macro.infixOp[OpId.!=]
//    def != (r : Double) : Boolean = macro Builder.Macro.infixOp[OpId.!=]
//
//    def unary_- : Int = macro Builder.Macro.prefixOp[OpId.Negate]
//    def toChar : Char = macro Builder.Macro.prefixOp[OpId.ToChar]
//    def toLong : Long = macro Builder.Macro.prefixOp[OpId.ToLong]
//    def toFloat : Float = macro Builder.Macro.prefixOp[OpId.ToFloat]
//    def toDouble : Double = macro Builder.Macro.prefixOp[OpId.ToDouble]
//    def toStringTF : String = macro Builder.Macro.prefixOp[OpId.ToString]
  }
  final class _Int[T <: scala.Int](val value : scala.Int) extends AnyVal with TwoFaceAny.Int[T] {
    @inline def getValue : scala.Int = value
  }
  implicit object Int extends TwoFaceAny.Builder[Int, scala.Int, Shell.Two.Int] {
    protected[singleton] def create[T <: scala.Int](value : scala.Int) : Int[T] = new _Int[T](value)
//    def numberOfLeadingZeros[T](r : Int) : Int = macro Builder.Macro.unaryOp[OpId.NumberOfLeadingZeros]
  }

  trait Long[T <: scala.Long] extends Any with TwoFaceAny[scala.Long, T] {
//    def == [R <: XChar](r : R)(
//      implicit tfo : Boolean.Return[T == R]
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Char)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XInt](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Int)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XLong](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Long)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XFloat](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Float)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XDouble](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Double)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//      di9 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//
//    def +  [R](r : Char[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Char[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Char[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Char[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Char[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
//    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Int[R])(implicit tfo : Long.Return[T + R])        = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Int[R])(implicit tfo : Long.Return[T - R])        = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Int[R])(implicit tfo : Long.Return[T * R])        = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Int[R])(implicit tfo : Long.Return[T / R])        = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Int[R])(implicit tfo : Long.Return[T % R])        = tfo(this.getValue %  r.getValue)
//    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
//    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
//    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Long[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Long[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Long[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Long[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Long[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
//    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
//    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
//    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
//    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
//    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
//    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
//    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
//    def min [R](r : Long[R])(implicit tfo : Long.Return[T Min R])    = tfo(this.getValue min r.getValue)
//    def max [R](r : Long[R])(implicit tfo : Long.Return[T Max R])    = tfo(this.getValue max r.getValue)
//    def unary_-            (implicit tfo : Long.Return[Negate[T]])   = tfo(-this.getValue)
//    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue.toChar)
//    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
//    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
//    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
//    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
  }
  final class _Long[T <: scala.Long](val value : scala.Long) extends AnyVal with TwoFaceAny.Long[T] {
    @inline def getValue : scala.Long = value
  }
  implicit object Long extends TwoFaceAny.Builder[Long, scala.Long, Shell.Two.Long] {
    protected[singleton] def create[T <: scala.Long](value : scala.Long) = new _Long[T](value)
//    def numberOfLeadingZeros[T](t : Long[T])(implicit tfo : Int.Return[NumberOfLeadingZeros[T]]) =
//      tfo(java.lang.Long.numberOfLeadingZeros(t.getValue))
  }

  trait Float[T <: scala.Float] extends Any with TwoFaceAny[scala.Float, T] {
//    def == [R <: XChar](r : R)(
//      implicit tfo : Boolean.Return[T == R]
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Char)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XInt](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Int)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XLong](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Long)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XFloat](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Float)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XDouble](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Double)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//      di9 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//
//    def +  [R](r : Char[R])(implicit tfo : Float.Return[T + R])      = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Char[R])(implicit tfo : Float.Return[T - R])      = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Char[R])(implicit tfo : Float.Return[T * R])      = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Char[R])(implicit tfo : Float.Return[T / R])      = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Char[R])(implicit tfo : Float.Return[T % R])      = tfo(this.getValue %  r.getValue)
//    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Int[R])(implicit tfo : Float.Return[T + R])       = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Int[R])(implicit tfo : Float.Return[T - R])       = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Int[R])(implicit tfo : Float.Return[T * R])       = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Int[R])(implicit tfo : Float.Return[T / R])       = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Int[R])(implicit tfo : Float.Return[T % R])       = tfo(this.getValue %  r.getValue)
//    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
//    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
//    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Long[R])(implicit tfo : Float.Return[T + R])      = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Long[R])(implicit tfo : Float.Return[T - R])      = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Long[R])(implicit tfo : Float.Return[T * R])      = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Long[R])(implicit tfo : Float.Return[T / R])      = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Long[R])(implicit tfo : Float.Return[T % R])      = tfo(this.getValue %  r.getValue)
//    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
//    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
//    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
//    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
//    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
//    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
//    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
//    def min [R](r : Float[R])(implicit tfo : Float.Return[T Min R])  = tfo(this.getValue min r.getValue)
//    def max [R](r : Float[R])(implicit tfo : Float.Return[T Max R])  = tfo(this.getValue max r.getValue)
//    def unary_-            (implicit tfo : Float.Return[Negate[T]])  = tfo(-this.getValue)
//    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue.toChar)
//    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
//    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
//    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
//    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
  }
  final class _Float[T <: scala.Float](val value : scala.Float) extends AnyVal with TwoFaceAny.Float[T] {
    @inline def getValue : scala.Float = value
  }
  implicit object Float extends TwoFaceAny.Builder[Float, scala.Float, Shell.Two.Float] {
    protected[singleton] def create[T <: scala.Float](value : scala.Float) = new _Float[T](value)
  }

  trait Double[T <: scala.Double] extends Any with TwoFaceAny[scala.Double, T] {
//    def == [R <: XChar](r : R)(
//      implicit tfo : Boolean.Return[T == R]
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Char)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XInt](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Int)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def == [R <: XLong](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Long)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XFloat](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Float)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == [R <: XDouble](r : R)(
//      implicit tfo : Boolean.Return[T == R],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Double)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit,
//      di2 : DummyImplicit,
//      di3 : DummyImplicit,
//      di4 : DummyImplicit,
//      di5 : DummyImplicit,
//      di6 : DummyImplicit,
//      di7 : DummyImplicit,
//      di8 : DummyImplicit,
//      di9 : DummyImplicit,
//    ) = tfo(this.getValue == r)
//
//    def +  [R](r : Char[R])(implicit tfo : Double.Return[T + R])     = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Char[R])(implicit tfo : Double.Return[T - R])     = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Char[R])(implicit tfo : Double.Return[T * R])     = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Char[R])(implicit tfo : Double.Return[T / R])     = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Char[R])(implicit tfo : Double.Return[T % R])     = tfo(this.getValue %  r.getValue)
//    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Int[R])(implicit tfo : Double.Return[T + R])      = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Int[R])(implicit tfo : Double.Return[T - R])      = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Int[R])(implicit tfo : Double.Return[T * R])      = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Int[R])(implicit tfo : Double.Return[T / R])      = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Int[R])(implicit tfo : Double.Return[T % R])      = tfo(this.getValue %  r.getValue)
//    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
//    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
//    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Long[R])(implicit tfo : Double.Return[T + R])     = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Long[R])(implicit tfo : Double.Return[T - R])     = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Long[R])(implicit tfo : Double.Return[T * R])     = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Long[R])(implicit tfo : Double.Return[T / R])     = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Long[R])(implicit tfo : Double.Return[T % R])     = tfo(this.getValue %  r.getValue)
//    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
//    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
//    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Float[R])(implicit tfo : Double.Return[T + R])    = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Float[R])(implicit tfo : Double.Return[T - R])    = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Float[R])(implicit tfo : Double.Return[T * R])    = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Float[R])(implicit tfo : Double.Return[T / R])    = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Float[R])(implicit tfo : Double.Return[T % R])    = tfo(this.getValue %  r.getValue)
//    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
//    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
//    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
//    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
//    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
//    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
//    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
//    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
//    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
//    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
//    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
//    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
//    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
//    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
//    def min [R](r : Double[R])(implicit tfo : Double.Return[T Min R])= tfo(this.getValue min r.getValue)
//    def max [R](r : Double[R])(implicit tfo : Double.Return[T Max R])= tfo(this.getValue max r.getValue)
//    def unary_-            (implicit tfo : Double.Return[Negate[T]]) = tfo(-this.getValue)
//    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue.toChar)
//    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
//    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
//    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
//    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
  }
  final class _Double[T <: scala.Double](val value : scala.Double) extends AnyVal with TwoFaceAny.Double[T] {
    @inline def getValue : scala.Double = value
  }
  implicit object Double extends TwoFaceAny.Builder[Double, scala.Double, Shell.Two.Double] {
    protected[singleton] def create[T <: scala.Double](value : scala.Double) = new _Double[T](value)
  }

  trait String[T <: java.lang.String] extends Any with TwoFaceAny[java.lang.String, T] {
//    def +  [R](r : String[R])(implicit tfo : String.Return[T + R])   = tfo(this.getValue +  r.getValue)
//    def == [R](r : String[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
//    def != [R](r : String[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
//    def == [R <: XString](r : R)(
//      implicit tfo : Boolean.Return[T == R]
//    ) = tfo(this.getValue == r)
//    def == (r : java.lang.String)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def reverse(implicit tfo : String.Return[Reverse[T]])            = tfo(this.getValue.reverse)
//    def substring[R](r : Int[R])(implicit tfo : String.Return[Substring[T,R]])= tfo(this.getValue substring r.getValue)
//    def length(implicit tfo : Int.Return[Length[T]])                 = tfo(this.getValue.length)
//    def charAt[R](r : Int[R])(implicit tfo : Char.Return[CharAt[T,R]])= tfo(this.getValue charAt r.getValue)
//    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
//    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
//    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
//    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
  }
  final class _String[T <: java.lang.String](val value : java.lang.String) extends AnyVal with TwoFaceAny.String[T] {
    @inline def getValue : java.lang.String = value
  }
  implicit object String extends TwoFaceAny.Builder[String, java.lang.String, Shell.Two.String] {
    protected[singleton] def create[T <: java.lang.String](value : java.lang.String) = new _String[T](value)
  }

  trait Boolean[T <: scala.Boolean] extends Any with TwoFaceAny[scala.Boolean, T] {
//    def == [R](r : Boolean[R])(implicit tfo : Boolean.Return[T == R])= tfo(this.getValue == r.getValue)
//    def != [R](r : Boolean[R])(implicit tfo : Boolean.Return[T != R])= tfo(this.getValue != r.getValue)
//    def && [R](r : Boolean[R])(implicit tfo : Boolean.Return[T && R])= tfo(this.getValue && r.getValue)
//    def || [R](r : Boolean[R])(implicit tfo : Boolean.Return[T || R])= tfo(this.getValue || r.getValue)
//    def unary_!(implicit tfo : Boolean.Return[![T]])= tfo(!this.getValue)
//    def == [R <: XBoolean](r : R)(
//      implicit tfo : Boolean.Return[T == R]
//    ) = tfo(this.getValue == r)
//    def == (r : scala.Boolean)(
//      implicit tfo : Boolean.Return[scala.Boolean],
//      di1 : DummyImplicit
//    ) = tfo(this.getValue == r)
//    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
  }
  final class _Boolean[T <: scala.Boolean](val value : scala.Boolean) extends AnyVal with TwoFaceAny.Boolean[T] {
    @inline def getValue : scala.Boolean = value
  }
  implicit object Boolean extends TwoFaceAny.Builder[Boolean, scala.Boolean, Shell.Two.Boolean] {
    protected[singleton] def create[T <: scala.Boolean](value : scala.Boolean) = new _Boolean[T](value)
  }

}
