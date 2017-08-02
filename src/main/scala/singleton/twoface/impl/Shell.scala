//package singleton.twoface.impl
//
//import macrocompat.bundle
//import singleton.ops.impl.GeneralMacros
//import scala.reflect.macros.whitebox
//
//////////////////////////////////////////////////////////////////////////////////
//// The code duplication in `Shell` can indeed be reduced.
//// However, the duplication was favored because it had caused less 'red' in
//// IntelliJ, when the `apply` in the `Shell` traits has a concrete return type.
//// Example red code without concrete return type:
//// def testme[L <: Int,R <: Int](l : TwoFace.Int[L], r : TwoFace.Int[R])
////     (implicit tfs : TwoFace.Int.Shell2[+,L,Int,R,Int]) = tfs(l,r)
//// val tmres = testme(TwoFace.Int(2),testme(TwoFace.Int(2),TwoFace.Int(2)))
//////////////////////////////////////////////////////////////////////////////////
//object Shell {
////  @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
////  trait One[FuncApply, FuncArgs, Arg1, Arg1Wide] {
////    type Out
////    type TF[T]
////    def apply(arg1 : Arg1Wide) : TF[Out]
////  }
////
////  @bundle
//  object One { //One Parameter Shell
//    type F[_]
////    type Aux[FuncApply, FuncArgs, Arg1, Arg1Wide, Ret_Out, Ret_TF[T]] =
////      One[FuncApply, FuncArgs, Arg1, Arg1Wide] {
////        type Out = Ret_Out
////        type TF[T] = Ret_TF[T]
////      }
////    implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Ret_Out, Ret_TF[T]]:
////    Aux[FuncApply, FuncArgs, Arg1, Arg1Wide, Ret_Out, Ret_TF] =
////    macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
////
////    final class Macro(val c: whitebox.Context) extends GeneralMacros {
////      def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
////      (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
////       arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
////        One[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell()
////    }
////
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Char[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.Char[Out]
//    }
//    @bundle
//    object Char {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      Char[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Char[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.Char.Shell1[F,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Int[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.Int[Out]
//    }
//    @bundle
//    object Int {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      Int[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Int[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.Int.Shell1[F,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Long[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.Long[Out]
//    }
//    @bundle
//    object Long {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      Long[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Long[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.Long.Shell1[F,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Float[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.Float[Out]
//    }
//    @bundle
//    object Float {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      Float[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Float[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.Float.Shell1[F,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Double[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.Double[Out]
//    }
//    @bundle
//    object Double {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      Double[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Double[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.Double.Shell1[F,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait String[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.String[Out]
//    }
//    @bundle
//    object String {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      String[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          String[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.String.Shell1[F,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide) : TwoFaceAny.Boolean[Out]
//    }
//    @bundle
//    object Boolean {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide]:
//      Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide]].shell(c.symbolOf[TwoFaceAny.Boolean.Shell1[F,_,_]])
//      }
//    }
//  }
//
//
////  @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
////  trait Two[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
////    type Out
////    type TF[T]
////    def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TF[Out]
////  }
////
//  object Two { //Two Parameters Shell
//    type F[_,_]
////    type Aux[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Ret_Out, Ret_TF[T]] =
////      Two[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
////        type Out = Ret_Out
////        type TF[T] = Ret_TF[T]
////      }
////    implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Ret_Out, Ret_TF[T]]:
////    Aux[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Ret_Out, Ret_TF] =
////    macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
////
////    final class Macro(val c: whitebox.Context) extends GeneralMacros {
////      def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
////      (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
////       arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
////       arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
////        Two[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell()
////    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Char[Out]
//    }
//    @bundle
//    object Char {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.Char.Shell2[F,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Int[Out]
//    }
//    @bundle
//    object Int {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.Int.Shell2[F,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Long[Out]
//    }
//    @bundle
//    object Long {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.Long.Shell2[F,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Float[Out]
//    }
//    @bundle
//    object Float {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.Float.Shell2[F,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Double[Out]
//    }
//    @bundle
//    object Double {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.String.Shell2[F,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.String[Out]
//    }
//    @bundle
//    object String {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.Boolean.Shell2[F,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide) : TwoFaceAny.Boolean[Out]
//    }
//    @bundle
//    object Boolean {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]:
//      Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide]].shell(c.symbolOf[TwoFaceAny.Boolean.Shell2[F,_,_,_,_]])
//      }
//    }
//  }
//
//
//
////  @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
////  trait Three[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
////    type Out
////    type TF[T]
////    def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TF[Out]
////  }
//
//  @bundle
//  object Three { //Two Parameters Shell
//    type F[_,_,_]
////    type Aux[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide, Ret_Out, Ret_TF[T]] =
////      Three[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
////        type Out = Ret_Out
////        type TF[T] = Ret_TF[T]
////      }
////    implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide, Ret_Out, Ret_TF[T]]:
////    Aux[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide, Ret_Out, Ret_TF] =
////    macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
////
////    final class Macro(val c: whitebox.Context) extends GeneralMacros {
////      def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
////      (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
////       arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
////       arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
////       arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
////        Three[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell()
////    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.Char[Out]
//    }
//    @bundle
//    object Char {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Char[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.Char.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.Int[Out]
//    }
//    @bundle
//    object Int {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Int[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.Int.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.Long[Out]
//    }
//    @bundle
//    object Long {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Long[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.Long.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.Float[Out]
//    }
//    @bundle
//    object Float {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Float[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.Float.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.Double[Out]
//    }
//    @bundle
//    object Double {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Double[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.Double.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.String[Out]
//    }
//    @bundle
//    object String {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          String[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.String.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//
//    @scala.annotation.implicitNotFound("Unable to create shell for TwoFace")
//    trait Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] {
//      type Out
//      def apply(arg1 : Arg1Wide, arg2 : Arg2Wide, arg3 : Arg3Wide) : TwoFaceAny.Boolean[Out]
//    }
//    @bundle
//    object Boolean {
//      implicit def ev[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]:
//      Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide] =
//      macro Macro.shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//
//      final class Macro(val c: whitebox.Context) extends GeneralMacros {
//        def shell[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]
//        (implicit funcApply : c.WeakTypeTag[FuncApply], func : c.WeakTypeTag[FuncArgs],
//         arg1 : c.WeakTypeTag[Arg1], arg1Wide : c.WeakTypeTag[Arg1Wide], arg2 : c.WeakTypeTag[Arg2],
//         arg2Wide : c.WeakTypeTag[Arg2Wide], arg3 : c.WeakTypeTag[Arg3],
//         arg3Wide : c.WeakTypeTag[Arg3Wide]) : c.Tree = TwoFaceShellMaterializer[
//          Boolean[FuncApply, FuncArgs, Arg1, Arg1Wide, Arg2, Arg2Wide, Arg3, Arg3Wide]].shell(c.symbolOf[TwoFaceAny.Boolean.Shell3[F,_,_,_,_,_,_]])
//      }
//    }
//  }
//}
