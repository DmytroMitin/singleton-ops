package singleton.twoface

import singleton.ops._

object TwoFace {
  sealed trait TwoFaceAny[Face, T] extends Any {
    def isLiteral(implicit rt : RunTime[T]) : scala.Boolean = !rt
    @inline def getValue : Face
    override def toString = getValue.toString
  }

  sealed trait TwoFaceBuilder[TF[_], Face] {
    type Return[OP] = TwoFaceOp[TF, Face, OP]
    def apply[T](value : Face) : TF[T]
    implicit def safe[T <: Face with Singleton](value : T)(implicit tfb : TwoFaceBuilder[TF, Face]) : TF[T] =
      tfb[T](value)
    implicit def unsafe[T <: Face](value : T)(implicit tfb : TwoFaceBuilder[TF, Face]) : TF[Face] =
      tfb[Face](value)
    implicit def ev[T](implicit si : SafeCast[Face with Singleton, T], tfb : TwoFaceBuilder[TF, Face]) : TF[si.Out] =
      tfb[si.Out](si.value)
  }

  sealed trait TwoFaceOp[TF[_], Face, OP] {
    type FB <: FallBack[Face, OP]
    val fb : FB
    def apply(op : => Face)(implicit tfb : TwoFaceBuilder[TF, Face]) : TF[fb.Out] =
      tfb[fb.Out](if (fb.isLiteral) fb.value.get else op)
  }
  object TwoFaceOp {
    implicit def ev[TF[_], Face, OP](implicit fb0 : FallBack[Face, OP]) : TwoFaceOp[TF, Face, OP]{type FB = fb0.type} =
      new TwoFaceOp[TF, Face, OP]{type FB = fb0.type; val fb : FB = fb0}
  }

  final class Char[T] private(val value : scala.Char) extends AnyVal with TwoFaceAny[scala.Char, T] {
    def +  [R](r : Char[R])(implicit tfo : Int.Return[T + R])        = tfo(this.getValue +  r.getValue)
    def -  [R](r : Char[R])(implicit tfo : Int.Return[T - R])        = tfo(this.getValue -  r.getValue)
    def *  [R](r : Char[R])(implicit tfo : Int.Return[T * R])        = tfo(this.getValue *  r.getValue)
    def /  [R](r : Char[R])(implicit tfo : Int.Return[T / R])        = tfo(this.getValue /  r.getValue)
    def %  [R](r : Char[R])(implicit tfo : Int.Return[T % R])        = tfo(this.getValue %  r.getValue)
    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Int[R])(implicit tfo : Int.Return[T + R])         = tfo(this.getValue +  r.getValue)
    def -  [R](r : Int[R])(implicit tfo : Int.Return[T - R])         = tfo(this.getValue -  r.getValue)
    def *  [R](r : Int[R])(implicit tfo : Int.Return[T * R])         = tfo(this.getValue *  r.getValue)
    def /  [R](r : Int[R])(implicit tfo : Int.Return[T / R])         = tfo(this.getValue /  r.getValue)
    def %  [R](r : Int[R])(implicit tfo : Int.Return[T % R])         = tfo(this.getValue %  r.getValue)
    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
    def +  [R](r : Long[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
    def -  [R](r : Long[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
    def *  [R](r : Long[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
    def /  [R](r : Long[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
    def %  [R](r : Long[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
    def unary_-            (implicit tfo : Int.Return[Negate[T]])    = tfo(-this.getValue)
    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue)
    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
    @inline def getValue : scala.Char = value
  }
  implicit object Char extends TwoFaceBuilder[Char, scala.Char] {
    def apply[T](value : scala.Char) = new Char[T](value)
  }

  final class Int[T] private[twoface](val value : scala.Int) extends AnyVal with TwoFaceAny[scala.Int, T] {
    def +  [R](r : Char[R])(implicit tfo : Int.Return[T + R])        = tfo(this.getValue +  r.getValue)
    def -  [R](r : Char[R])(implicit tfo : Int.Return[T - R])        = tfo(this.getValue -  r.getValue)
    def *  [R](r : Char[R])(implicit tfo : Int.Return[T * R])        = tfo(this.getValue *  r.getValue)
    def /  [R](r : Char[R])(implicit tfo : Int.Return[T / R])        = tfo(this.getValue /  r.getValue)
    def %  [R](r : Char[R])(implicit tfo : Int.Return[T % R])        = tfo(this.getValue %  r.getValue)
    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Int[R])(implicit tfo : Int.Return[T + R])         = tfo(this.getValue +  r.getValue)
    def -  [R](r : Int[R])(implicit tfo : Int.Return[T - R])         = tfo(this.getValue -  r.getValue)
    def *  [R](r : Int[R])(implicit tfo : Int.Return[T * R])         = tfo(this.getValue *  r.getValue)
    def /  [R](r : Int[R])(implicit tfo : Int.Return[T / R])         = tfo(this.getValue /  r.getValue)
    def %  [R](r : Int[R])(implicit tfo : Int.Return[T % R])         = tfo(this.getValue %  r.getValue)
    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
    def +  [R](r : Long[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
    def -  [R](r : Long[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
    def *  [R](r : Long[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
    def /  [R](r : Long[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
    def %  [R](r : Long[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
    def min [R](r : Int[R])(implicit tfo : Int.Return[T Min R])      = tfo(this.getValue min r.getValue)
    def max [R](r : Int[R])(implicit tfo : Int.Return[T Max R])      = tfo(this.getValue max r.getValue)
    def unary_-            (implicit tfo : Int.Return[Negate[T]])    = tfo(-this.getValue)
    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue.toChar)
    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue)
    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
    @inline def getValue : scala.Int = value
  }
  implicit object Int extends TwoFaceBuilder[Int, scala.Int] {
    def apply[T](value : scala.Int) = new Int[T](value)
  }

  final class Long[T] private[twoface](val value : scala.Long) extends AnyVal with TwoFaceAny[scala.Long, T] {
    def +  [R](r : Char[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
    def -  [R](r : Char[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
    def *  [R](r : Char[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
    def /  [R](r : Char[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
    def %  [R](r : Char[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Int[R])(implicit tfo : Long.Return[T + R])        = tfo(this.getValue +  r.getValue)
    def -  [R](r : Int[R])(implicit tfo : Long.Return[T - R])        = tfo(this.getValue -  r.getValue)
    def *  [R](r : Int[R])(implicit tfo : Long.Return[T * R])        = tfo(this.getValue *  r.getValue)
    def /  [R](r : Int[R])(implicit tfo : Long.Return[T / R])        = tfo(this.getValue /  r.getValue)
    def %  [R](r : Int[R])(implicit tfo : Long.Return[T % R])        = tfo(this.getValue %  r.getValue)
    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
    def +  [R](r : Long[R])(implicit tfo : Long.Return[T + R])       = tfo(this.getValue +  r.getValue)
    def -  [R](r : Long[R])(implicit tfo : Long.Return[T - R])       = tfo(this.getValue -  r.getValue)
    def *  [R](r : Long[R])(implicit tfo : Long.Return[T * R])       = tfo(this.getValue *  r.getValue)
    def /  [R](r : Long[R])(implicit tfo : Long.Return[T / R])       = tfo(this.getValue /  r.getValue)
    def %  [R](r : Long[R])(implicit tfo : Long.Return[T % R])       = tfo(this.getValue %  r.getValue)
    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
    def min [R](r : Long[R])(implicit tfo : Long.Return[T Min R])    = tfo(this.getValue min r.getValue)
    def max [R](r : Long[R])(implicit tfo : Long.Return[T Max R])    = tfo(this.getValue max r.getValue)
    def unary_-            (implicit tfo : Long.Return[Negate[T]])   = tfo(-this.getValue)
    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue.toChar)
    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue)
    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue.toFloat)
    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
    @inline def getValue : scala.Long = value
  }
  implicit object Long extends TwoFaceBuilder[Long, scala.Long] {
    def apply[T](value : scala.Long) = new Long[T](value)
  }

  final class Float[T] private[twoface](val value : scala.Float) extends AnyVal with TwoFaceAny[scala.Float, T] {
    def +  [R](r : Char[R])(implicit tfo : Float.Return[T + R])      = tfo(this.getValue +  r.getValue)
    def -  [R](r : Char[R])(implicit tfo : Float.Return[T - R])      = tfo(this.getValue -  r.getValue)
    def *  [R](r : Char[R])(implicit tfo : Float.Return[T * R])      = tfo(this.getValue *  r.getValue)
    def /  [R](r : Char[R])(implicit tfo : Float.Return[T / R])      = tfo(this.getValue /  r.getValue)
    def %  [R](r : Char[R])(implicit tfo : Float.Return[T % R])      = tfo(this.getValue %  r.getValue)
    def == [R](r : Char[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Char[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Char[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Char[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Char[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Char[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Int[R])(implicit tfo : Float.Return[T + R])       = tfo(this.getValue +  r.getValue)
    def -  [R](r : Int[R])(implicit tfo : Float.Return[T - R])       = tfo(this.getValue -  r.getValue)
    def *  [R](r : Int[R])(implicit tfo : Float.Return[T * R])       = tfo(this.getValue *  r.getValue)
    def /  [R](r : Int[R])(implicit tfo : Float.Return[T / R])       = tfo(this.getValue /  r.getValue)
    def %  [R](r : Int[R])(implicit tfo : Float.Return[T % R])       = tfo(this.getValue %  r.getValue)
    def == [R](r : Int[R])(implicit tfo : Boolean.Return[T == R])    = tfo(this.getValue == r.getValue)
    def != [R](r : Int[R])(implicit tfo : Boolean.Return[T != R])    = tfo(this.getValue != r.getValue)
    def <  [R](r : Int[R])(implicit tfo : Boolean.Return[T <  R])    = tfo(this.getValue <  r.getValue)
    def >  [R](r : Int[R])(implicit tfo : Boolean.Return[T >  R])    = tfo(this.getValue >  r.getValue)
    def <= [R](r : Int[R])(implicit tfo : Boolean.Return[T <= R])    = tfo(this.getValue <= r.getValue)
    def >= [R](r : Int[R])(implicit tfo : Boolean.Return[T >= R])    = tfo(this.getValue >= r.getValue)
    def +  [R](r : Long[R])(implicit tfo : Float.Return[T + R])      = tfo(this.getValue +  r.getValue)
    def -  [R](r : Long[R])(implicit tfo : Float.Return[T - R])      = tfo(this.getValue -  r.getValue)
    def *  [R](r : Long[R])(implicit tfo : Float.Return[T * R])      = tfo(this.getValue *  r.getValue)
    def /  [R](r : Long[R])(implicit tfo : Float.Return[T / R])      = tfo(this.getValue /  r.getValue)
    def %  [R](r : Long[R])(implicit tfo : Float.Return[T % R])      = tfo(this.getValue %  r.getValue)
    def == [R](r : Long[R])(implicit tfo : Boolean.Return[T == R])   = tfo(this.getValue == r.getValue)
    def != [R](r : Long[R])(implicit tfo : Boolean.Return[T != R])   = tfo(this.getValue != r.getValue)
    def <  [R](r : Long[R])(implicit tfo : Boolean.Return[T <  R])   = tfo(this.getValue <  r.getValue)
    def >  [R](r : Long[R])(implicit tfo : Boolean.Return[T >  R])   = tfo(this.getValue >  r.getValue)
    def <= [R](r : Long[R])(implicit tfo : Boolean.Return[T <= R])   = tfo(this.getValue <= r.getValue)
    def >= [R](r : Long[R])(implicit tfo : Boolean.Return[T >= R])   = tfo(this.getValue >= r.getValue)
    def +  [R](r : Float[R])(implicit tfo : Float.Return[T + R])     = tfo(this.getValue +  r.getValue)
    def -  [R](r : Float[R])(implicit tfo : Float.Return[T - R])     = tfo(this.getValue -  r.getValue)
    def *  [R](r : Float[R])(implicit tfo : Float.Return[T * R])     = tfo(this.getValue *  r.getValue)
    def /  [R](r : Float[R])(implicit tfo : Float.Return[T / R])     = tfo(this.getValue /  r.getValue)
    def %  [R](r : Float[R])(implicit tfo : Float.Return[T % R])     = tfo(this.getValue %  r.getValue)
    def == [R](r : Float[R])(implicit tfo : Boolean.Return[T == R])  = tfo(this.getValue == r.getValue)
    def != [R](r : Float[R])(implicit tfo : Boolean.Return[T != R])  = tfo(this.getValue != r.getValue)
    def <  [R](r : Float[R])(implicit tfo : Boolean.Return[T <  R])  = tfo(this.getValue <  r.getValue)
    def >  [R](r : Float[R])(implicit tfo : Boolean.Return[T >  R])  = tfo(this.getValue >  r.getValue)
    def <= [R](r : Float[R])(implicit tfo : Boolean.Return[T <= R])  = tfo(this.getValue <= r.getValue)
    def >= [R](r : Float[R])(implicit tfo : Boolean.Return[T >= R])  = tfo(this.getValue >= r.getValue)
    def +  [R](r : Double[R])(implicit tfo : Double.Return[T + R])   = tfo(this.getValue +  r.getValue)
    def -  [R](r : Double[R])(implicit tfo : Double.Return[T - R])   = tfo(this.getValue -  r.getValue)
    def *  [R](r : Double[R])(implicit tfo : Double.Return[T * R])   = tfo(this.getValue *  r.getValue)
    def /  [R](r : Double[R])(implicit tfo : Double.Return[T / R])   = tfo(this.getValue /  r.getValue)
    def %  [R](r : Double[R])(implicit tfo : Double.Return[T % R])   = tfo(this.getValue %  r.getValue)
    def == [R](r : Double[R])(implicit tfo : Boolean.Return[T == R]) = tfo(this.getValue == r.getValue)
    def != [R](r : Double[R])(implicit tfo : Boolean.Return[T != R]) = tfo(this.getValue != r.getValue)
    def <  [R](r : Double[R])(implicit tfo : Boolean.Return[T <  R]) = tfo(this.getValue <  r.getValue)
    def >  [R](r : Double[R])(implicit tfo : Boolean.Return[T >  R]) = tfo(this.getValue >  r.getValue)
    def <= [R](r : Double[R])(implicit tfo : Boolean.Return[T <= R]) = tfo(this.getValue <= r.getValue)
    def >= [R](r : Double[R])(implicit tfo : Boolean.Return[T >= R]) = tfo(this.getValue >= r.getValue)
    def min [R](r : Float[R])(implicit tfo : Float.Return[T Min R])  = tfo(this.getValue min r.getValue)
    def max [R](r : Float[R])(implicit tfo : Float.Return[T Max R])  = tfo(this.getValue max r.getValue)
    def unary_-            (implicit tfo : Float.Return[Negate[T]])  = tfo(-this.getValue)
    def toChar(implicit tfo : Char.Return[ToChar[T]])                = tfo(this.getValue.toChar)
    def toInt(implicit tfo : Int.Return[ToInt[T]])                   = tfo(this.getValue.toInt)
    def toLong(implicit tfo : Long.Return[ToLong[T]])                = tfo(this.getValue.toLong)
    def toFloat(implicit tfo : Float.Return[ToFloat[T]])             = tfo(this.getValue)
    def toDouble(implicit tfo : Double.Return[ToDouble[T]])          = tfo(this.getValue.toDouble)
    def toString(implicit tfo : String.Return[ToString[T]])          = tfo(this.getValue.toString)
    @inline def getValue : scala.Float = value
  }
  implicit object Float extends TwoFaceBuilder[Float, scala.Float] {
    def apply[T](value : scala.Float) = new Float[T](value)
  }

  final class Double[T] private[twoface](val value : scala.Double) extends AnyVal with TwoFaceAny[scala.Double, T] {
    @inline def getValue : scala.Double = value
  }
  implicit object Double extends TwoFaceBuilder[Double, scala.Double] {
    def apply[T](value : scala.Double) = new Double[T](value)
  }

  final class String[T] private[twoface](val value : java.lang.String) extends AnyVal with TwoFaceAny[java.lang.String, T] {
    @inline def getValue : java.lang.String = value
  }
  implicit object String extends TwoFaceBuilder[String, java.lang.String] {
    def apply[T](value : java.lang.String) = new String[T](value)
  }

  final class Boolean[T] private[twoface](val value : scala.Boolean) extends AnyVal with TwoFaceAny[scala.Boolean, T] {
    @inline def getValue : scala.Boolean = value
  }
  implicit object Boolean extends TwoFaceBuilder[Boolean, scala.Boolean] {
    def apply[T](value : scala.Boolean) = new Boolean[T](value)
  }
}


