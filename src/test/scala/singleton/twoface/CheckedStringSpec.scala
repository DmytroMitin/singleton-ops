package singleton.twoface

import org.scalacheck.Properties
import shapeless.test.illTyped
import singleton.TestUtils._
import singleton.ops._

object CheckedStringSpec {
  object LengthSmallerThan extends Checked1Param.String {
    type Cond[T, P] = Length[T] < P
    type Msg[T, P] = W.`"Length of string '"`.T + T + W.`"' is not smaller than "`.T + ToString[P]
    type ParamFace = Int
  }
}

class CheckedStringSpec extends Properties("Checked.String") {
  import CheckedStringSpec._

  def foo[T](t : TwoFace.String[T]) : Unit = {}
  def lengthSmallerThan5[T](t : LengthSmallerThan.Checked[T,W.`5`.T]) : Unit = {
    val temp : String = t
    foo(t.unsafeCheck(5))
  }

  property("Compile-time checks") = wellTyped {
    lengthSmallerThan5("Hi")
    lengthSmallerThan5(TwoFace.String("Hi"))
    illTyped("""lengthSmallerThan5("Hello")""","Length of string 'Hello' is not smaller than 5")
    illTyped("""lengthSmallerThan5(TwoFace.String("Hello"))""","Length of string 'Hello' is not smaller than 5")
  }

  property("Run-time checks") = wellTyped {
    lengthSmallerThan5(us("Hi"))
    lengthSmallerThan5(TwoFace.String(us("Hi")))
    illRun{lengthSmallerThan5(us("Hello"))}
    illRun{lengthSmallerThan5(TwoFace.String(us("Hello")))}
  }

  def lengthSmallerThan5Impl[T](realValue : String)(implicit t : LengthSmallerThan.CheckedShell[T,W.`5`.T]) : Unit =
    {t.unsafeCheck(realValue, 5)}

  property("Shell compile-time checks") = wellTyped {
    lengthSmallerThan5Impl[W.`"Hi"`.T](us("Hi"))
    illTyped("""lengthSmallerThan5Impl[W.`"Hello"`.T](us("Hello"))""", "Length of string 'Hello' is not smaller than 5")
  }

  property("Shell run-time checks") = wellTyped {
    lengthSmallerThan5Impl[String](us("Hi"))
    illRun{lengthSmallerThan5Impl[String](us("Hello"))}
  }

  trait CheckedUse[T]
  object CheckedUse {
    implicit def ev[T](implicit checkedTrue: LengthSmallerThan.CheckedShellSym[CheckedUse[_], T, W.`5`.T]) : CheckedUse[T] =
      new CheckedUse[T] {}
  }

  property("Shell user message redirect checks") = wellTyped {
    implicitly[CheckedUse[W.`"Hi"`.T]]
    illTyped("""implicitly[CheckedUse[W.`"Hello"`.T]]""", "Length of string 'Hello' is not smaller than 5")
  }

}
