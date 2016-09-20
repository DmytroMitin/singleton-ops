package singleton
import shapeless.Nat
import singleton.ops.impl._

package object ops {
  /////////////////////////////////////////////////
  //Short aliases of singleton types
  /////////////////////////////////////////////////
  type XChar                = Char with Singleton
  type XInt                 = Int with Singleton
  type XLong                = Long with Singleton
  type XFloat               = Float with Singleton
  type XDouble              = Double with Singleton
  type XString              = String with Singleton
  type XBoolean             = Boolean with Singleton
  /////////////////////////////////////////////////

  /////////////////////////////////////////////////
  //Short aliases of auxiliary operation types
  /////////////////////////////////////////////////
  type OpAuxNat[O <: Op,      Ret_Out <: Nat]       = OpNat.Aux[O, Ret_Out]
  type OpAuxChar[O <: Op,     Ret_Out <: XChar]     = OpChar.Aux[O, Ret_Out]
  type OpAuxInt[O <: Op,      Ret_Out <: XInt]      = OpInt.Aux[O, Ret_Out]
  type OpAuxLong[O <: Op,     Ret_Out <: XLong]     = OpLong.Aux[O, Ret_Out]
  type OpAuxFloat[O <: Op,    Ret_Out <: XFloat]    = OpFloat.Aux[O, Ret_Out]
  type OpAuxDouble[O <: Op,   Ret_Out <: XDouble]   = OpDouble.Aux[O, Ret_Out]
  type OpAuxString[O <: Op,   Ret_Out <: XString]   = OpString.Aux[O, Ret_Out]
  type OpAuxBoolean[O <: Op,  Ret_Out <: XBoolean]  = OpBoolean.Aux[O, Ret_Out]
  /////////////////////////////////////////////////

  /////////////////////////////////////////////////
  //Special control aliases
  /////////////////////////////////////////////////
  type ITE[Cond,TBody,EBody]= OpMacro["ITE",Cond, TBody, EBody]
  type While[Cond,Body,Ret] = OpMacro["While",Cond, Body, Ret]
  type ==>[A,B]             = OpMacro["==>",A, B, 0]
  type :=[A,B]              = OpMacro[":=",A, B, 0]
  type +=[A,B]              = OpMacro["+=",A, B, 0]
  type SV[Name,Value]       = OpMacro["SV",Name, Value, 0]
  type GV[Name]             = OpMacro["GV",Name, 0, 0]
  /////////////////////////////////////////////////

  type ![P1]                = OpMacro["!",P1, 0, 0]
  type Require[P1]          = OpMacro["Require",P1, 0, 0]
  type ToNat[P1]            = OpMacro["ToNat",P1, 0, 0]
  type ToInt[P1]            = OpMacro["ToInt",P1, 0, 0]
  type ToLong[P1]           = OpMacro["ToLong",P1, 0, 0]
  type ToDouble[P1]         = OpMacro["ToDouble",P1, 0, 0]
  type Reverse[P1]          = OpMacro["Reverse",P1, 0, 0]
  type Negate[P1]           = OpMacro["Negate",P1, 0, 0]
  type Abs[P1]              = OpMacro["Abs",P1, 0, 0]

  type +[P1, P2]            = OpMacro["+",P1, P2, 0]
  type -[P1, P2]            = OpMacro["-",P1, P2, 0]
  type *[P1, P2]            = OpMacro["*",P1, P2, 0]
  type /[P1, P2]            = OpMacro["/",P1, P2, 0]
  type %[P1, P2]            = OpMacro["%",P1, P2, 0]
  type <[P1, P2]            = OpMacro["<",P1, P2, 0]
  type <=[P1, P2]           = OpMacro["<=",P1, P2, 0]
  type >=[P1, P2]           = OpMacro[">=",P1, P2, 0]
  type >[P1, P2]            = OpMacro[">",P1, P2, 0]
  type ==[P1, P2]           = OpMacro["==",P1, P2, 0]
  type !=[P1, P2]           = OpMacro["!=",P1, P2, 0]
  type &&[P1, P2]           = OpMacro["&&",P1, P2, 0]
  type ||[P1, P2]           = OpMacro["||",P1, P2, 0]
  type Min[P1, P2]          = OpMacro["Min",P1, P2, 0]
  type Max[P1, P2]          = OpMacro["Max",P1, P2, 0]
  type Substring[P1, P2]    = OpMacro["Substring",P1, P2, 0]
}
