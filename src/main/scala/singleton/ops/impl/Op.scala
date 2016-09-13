package singleton.ops.impl

import macrocompat.bundle

import scala.reflect.macros.whitebox
import singleton.ops.impl._

trait SingletonTypeExpr extends Serializable {
  type BaseType
  type Out <: BaseType with Singleton
  type OutInt <: Int with Singleton
  type OutLong <: Long with Singleton
  type OutDouble <: Double with Singleton
  type OutString <: String with Singleton
  val value: Out {}
  //For debugging only
  def outTypeName: String = {
    if (value.isInstanceOf[Int]) "Int"
    else "Unknown"
  }
//  match {
//    case (p.isInstanceOf[Int]) => "Int"
//    case (_ : Long with Singleton) => "Long"
//    case (_ : Double with Singleton) => "Double"
//    case (_ : String with Singleton) => "String"
//    case _ => "Unknown"
//  }
}

trait SingletonTypeExprBase[B] extends SingletonTypeExpr { type BaseType = B }


trait Repeater[P <: SingletonTypeExpr] extends SingletonTypeExpr

object Repeater {
  type Aux[P <: SingletonTypeExpr, Ret_BaseType, Ret_Out <: Ret_BaseType with Singleton] =
  Repeater[P] { type BaseType = Ret_BaseType; type Out = Ret_Out}

  implicit def impl[P <: SingletonTypeExpr, Ret_BaseType, Ret_Out <: Ret_BaseType with Singleton]
  (implicit p: P): Aux[P, p.BaseType, p.Out] = new Repeater[P] {
    type BaseType = p.BaseType; type Out = p.Out; val value: Out = p.value
  }
}

trait SingletonTypeValue[S] extends SingletonTypeExpr

object SingletonTypeValue {

  implicit def implExpr[P1 <: SingletonTypeExpr](
      implicit op: Repeater[P1]): SingletonTypeValue[P1] {
    type BaseType = op.BaseType; type Out = op.Out
  } =
    new SingletonTypeValue[P1] {
      type BaseType = op.BaseType; type Out = op.Out; val value: Out = op.value
    }

  implicit def implInt[S <: Int with Singleton]
  (implicit v: ValueOf[S]): SingletonTypeValue[S] {
    type BaseType = Int; type Out = S; type OutInt = S
  } = new SingletonTypeValue[S] {
    type BaseType = Int; type Out = S; type OutInt = S; val value: Out = valueOf[S]
  }

  implicit def implLong[S <: Long with Singleton]
  (implicit v: ValueOf[S], di1: DummyImplicit): SingletonTypeValue[S] {
    type BaseType = Long; type Out = S; type OutLong = S
  } = new SingletonTypeValue[S] {
    type BaseType = Long; type Out = S; type OutLong = S; val value: Out = valueOf[S]
  }

  implicit def implDouble[S <: Double with Singleton]
  (implicit v: ValueOf[S], di1: DummyImplicit, di2: DummyImplicit): SingletonTypeValue[S] {
    type BaseType = Double; type Out = S; type OutDouble = S
  } = new SingletonTypeValue[S] {
    type BaseType = Double; type Out = S; type OutDouble = S; val value: Out = valueOf[S]
  }

  implicit def implString[S <: String with Singleton]
  (implicit v: ValueOf[S], di1: DummyImplicit, di2: DummyImplicit, di3: DummyImplicit): SingletonTypeValue[S] {
    type BaseType = String; type Out = S; type OutString = S
  } = new SingletonTypeValue[S] {
    type BaseType = String; type Out = S; type OutString = S; val value: Out = valueOf[S]
  }
}


trait Return[S <: SingletonTypeExpr] extends SingletonTypeExpr

object Return {
  implicit def implExprInt[P1 <: SingletonTypeExpr, Ret_BaseType <: Int, Ret_Out <: Ret_BaseType with Singleton]
  (implicit op: Repeater.Aux[P1, Ret_BaseType, Ret_Out]): Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutInt = Ret_Out
  } = new Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutInt = Ret_Out; val value: Out = op.value
  }

  implicit def implExprLong[P1 <: SingletonTypeExpr, Ret_BaseType <: Long, Ret_Out <: Ret_BaseType with Singleton]
  (implicit op: Repeater.Aux[P1, Ret_BaseType, Ret_Out]): Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutLong = Ret_Out
  } = new Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutLong = Ret_Out; val value: Out = op.value
  }

  implicit def implExprDouble[P1 <: SingletonTypeExpr, Ret_BaseType <: Double, Ret_Out <: Ret_BaseType with Singleton]
  (implicit op: Repeater.Aux[P1, Ret_BaseType, Ret_Out]): Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutDouble = Ret_Out
  } = new Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutDouble = Ret_Out; val value: Out = op.value
  }

  implicit def implExprString[P1 <: SingletonTypeExpr, Ret_BaseType <: String, Ret_Out <: Ret_BaseType with Singleton]
  (implicit op: Repeater.Aux[P1, Ret_BaseType, Ret_Out]): Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutString = Ret_Out
  } = new Return[P1] {
    type BaseType = Ret_BaseType; type Out = Ret_Out; type OutString = Ret_Out; val value: Out = op.value
  }
}


trait SingletonTypeFunc1[N <: String with Singleton, P1 <: SingletonTypeExpr] extends SingletonTypeExpr
object SingletonTypeFunc1 {
  type Aux[
  N <: String with Singleton,
  P1 <: SingletonTypeExpr,
  Ret_BaseType,
  Ret_Out <: Ret_BaseType with Singleton
  ] = SingletonTypeFunc1[N, P1] {
    type BaseType = Ret_BaseType
    type Out = Ret_Out
  }

  implicit def impl[
  N <: String with Singleton,
  P1 <: SingletonTypeExpr,
  P1_BaseType,
  P1_Out <: P1_BaseType with Singleton,
  Ret_BaseType,
  Ret_Out <: Ret_BaseType with Singleton
  ](implicit
    p1_ret: Repeater.Aux[P1, P1_BaseType, P1_Out],
    op: Op1Macro[N,P1_BaseType, P1_Out])
  : Aux[N, P1, op.BaseType, op.Out] with SingletonTypeExpr =
    new SingletonTypeFunc1[N, P1] {
      type BaseType = op.BaseType
      type Out = op.Out
      val value: Out {} = op.value
    }
}



trait SingletonTypeFunc2[N <: String with Singleton, P1 <: SingletonTypeExpr, P2 <: SingletonTypeExpr]
  extends SingletonTypeExpr


object SingletonTypeFunc2 {
  type Aux[
  N <: String with Singleton,
  P1 <: SingletonTypeExpr,
  P2 <: SingletonTypeExpr,
  Ret_BaseType,
  Ret_Out <: Ret_BaseType with Singleton
  ] = SingletonTypeFunc2[N, P1, P2] {
    type BaseType = Ret_BaseType
    type Out = Ret_Out
  }

  implicit def impl[
  N <: String with Singleton,
  P1 <: SingletonTypeExpr,
  P2 <: SingletonTypeExpr,
  P1_BaseType,
  P1_Out <: P1_BaseType with Singleton,
  P2_BaseType,
  P2_Out <: P2_BaseType with Singleton,
  Ret_BaseType,
  Ret_Out <: Ret_BaseType with Singleton
  ](implicit
    p1_ret: Repeater.Aux[P1, P1_BaseType, P1_Out],
    p2_ret: Repeater.Aux[P2, P2_BaseType, P2_Out],
    op: Op2Macro[N,P1_BaseType, P1_Out, P2_BaseType, P2_Out])
  : Aux[N, P1, P2, op.BaseType, op.Out] with SingletonTypeExpr =
    new SingletonTypeFunc2[N, P1, P2] {
      type BaseType = op.BaseType
      type Out = op.Out
      val value: Out {} = op.value
    }
}



trait Op {
  type Out
  val value: Out {}
}

trait Op1[B, T1, S1 <: T1 with Singleton] extends SingletonTypeExprBase[B]
