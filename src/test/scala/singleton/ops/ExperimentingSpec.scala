package singleton.ops

object NewDemo {
  //////////////////////////////
  def demo[L <: XInt](implicit p : L*L + L) : p.Out = p.value
  val b : 30 = demo[5]
  //////////////////////////////

  //////////////////////////////
  def demoLong[L1 <: XLong, L2 <: XLong](implicit p : Min[L1*L1, L2+L2]) : p.Out {} = p.value
  val bLong1 : 1L = demoLong[1L, 5L]
  val bLong2 : 6L = demoLong[3L, 3L]
  //////////////////////////////

  //////////////////////////////
  def demoDouble[L1 <: XDouble, L2 <: XDouble](implicit p : L1 / L2 + 1.0) : p.Out = p.value
  val bDouble : 1.2 = demoDouble[1.0, 5.0]
  //////////////////////////////

  //////////////////////////////
  def demoSumLongInt[L1 <: XLong, L2 <: XInt](implicit p : L1 + L2) : p.Out = p.value
  val bSumLongInt : 16L = demoSumLongInt[8L, 8]
  //////////////////////////////

  //////////////////////////////
  def demoString[P1 <: XString](implicit op : Reverse[P1] + P1) : op.Out{} = op.value
  val bString : "cbaabc" = demoString["abc"]
  //////////////////////////////

  //////////////////////////////
  def demoBoolean[P1 <: XInt](implicit op : P1 < 0) : op.Out{} = op.value
  val bBoolean1 : true = demoBoolean[-5]
  val bBoolean2 : false = demoBoolean[5]
  val bBoolean3 : false = demoBoolean[0]
  //////////////////////////////

  //////////////////////////////
  def demoRequire[P1 <: XInt](implicit op : Require[P1 < 0]) : op.Out{} = op.value
  demoRequire[-1]
  //////////////////////////////

  //////////////////////////////
  import shapeless._
  val n = Nat(5)
  def demoNatToSingletonInt[L <: Nat](implicit p : L+L) : p.Out {} = p.value
  val bSing10 : 10 = demoNatToSingletonInt[n.N]
  def demoSigNatSig[L <: XInt](implicit op : ToNat[L+L]) : op.Out = op.value
  val bNat5 : shapeless.nat._10 = demoSigNatSig[5]
  //////////////////////////////
}

object RightTriangleDemo {
  type RightTriangle[A,B,C] =
    ((A * A) + (B * B) == (C * C)) ||
    ((A * A) + (C * C) == (B * B)) ||
    ((B * B) + (C * C) == (A * A))

  def fooWith90DegTriangle[A <: XDouble, B <: XDouble, C <: XDouble](implicit check : Require[RightTriangle[A,B,C]]) : Unit = {}
  fooWith90DegTriangle[5.0,3.0,5.830951894845301] //OK!
  //  fooWith90DegTriangle[5.0,3.1,5.830951894845301] //Error:(17, 23) could not find implicit value for parameter check: singleton.ops.Require[singleton.ops.ITESpec.RightTriangle[5.0,3.1,5.830951894845301]]
}


object FixedSizedVectorDemo {
  type CheckPositive[I] = Require[I > 0]
  object Method_One {
    class FixedSizeVector[L <: XInt]() {
      def concat[L2 <: XInt](that : FixedSizeVector[L2])(implicit l : L + L2) = new FixedSizeVector[l.OutInt]
      def + (that : FixedSizeVector[L]) = new FixedSizeVector[L]
      def printLength(implicit length : SafeInt[L]) : Unit = println("Vector length is: " + length)
    }

    object FixedSizeVector {
      def apply[L <: XInt](implicit check : CheckPositive[L]) = new FixedSizeVector[L]
    }

    object TestVector {
      val v1 = FixedSizeVector[5]
      val v2 = FixedSizeVector[2]
      val v3 : FixedSizeVector[40] = v1 concat v2 concat v1 concat v2 concat v1 concat v2 concat v1 concat v2 concat v1 concat v2 concat v1
      //  val v4 = FixedSizeVector[-1] //Will lead to error could not find implicit value for parameter check: singleton.ops.Require[singleton.ops.>[-1,0]]
    }
  }

  object Method_Two {
    class FixedSizeVector[L]() {
      def concat[L2](that : FixedSizeVector[L2]) = new FixedSizeVector[L + L2]
      def + (that : FixedSizeVector[L]) = new FixedSizeVector[L]
      def printLength(implicit length : SafeInt[L]) : Unit = println("Vector length is: " + length)
    }

    object FixedSizeVector {
      def apply[L](implicit check : CheckPositive[L]) = new FixedSizeVector[L]
      implicit def proof[L1, L2](fsz : FixedSizeVector[L1])(implicit check : Require[L1 == L2]) : FixedSizeVector[L2] = new FixedSizeVector[L2]
    }

    object TestVector {
      val v1 = FixedSizeVector[5]
      val v2 = FixedSizeVector[2]
      val v3 : FixedSizeVector[40] = v1 concat v2 concat v1 concat v2 concat v1 concat v2 concat v1 concat v2 concat v1 concat v2 concat v1
      //  val v4 = FixedSizeVector[-1] //Will lead to error could not find implicit value for parameter check: singleton.ops.Require[singleton.ops.>[-1,0]]
    }
  }
}

/* TODOs:
Fix real world matrix example
Add operations table to readme
Add Floor, Ceil
Add Log2 (efficient)
 */

