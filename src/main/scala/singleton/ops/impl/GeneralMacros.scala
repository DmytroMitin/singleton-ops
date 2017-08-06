package singleton.ops.impl
import macrocompat.bundle
import shapeless.tag
import shapeless.tag.@@
import singleton.twoface.impl.TwoFaceAny

import scala.reflect.macros.whitebox
@bundle
trait GeneralMacros {
  val c: whitebox.Context

  import c.universe._

  object funcTypes {
    val Arg = symbolOf[OpId.Arg]
    val AcceptNonLiteral = symbolOf[OpId.AcceptNonLiteral]
    val Id = symbolOf[OpId.Id]
    val ToNat = symbolOf[OpId.ToNat]
    val ToChar = symbolOf[OpId.ToChar]
    val ToInt = symbolOf[OpId.ToInt]
    val ToLong = symbolOf[OpId.ToLong]
    val ToFloat = symbolOf[OpId.ToFloat]
    val ToDouble = symbolOf[OpId.ToDouble]
    val ToString = symbolOf[OpId.ToString]
    val ToSymbol = symbolOf[OpId.ToSymbol]
    val IsNat = symbolOf[OpId.IsNat]
    val IsChar = symbolOf[OpId.IsChar]
    val IsInt = symbolOf[OpId.IsInt]
    val IsLong = symbolOf[OpId.IsLong]
    val IsFloat = symbolOf[OpId.IsFloat]
    val IsDouble = symbolOf[OpId.IsDouble]
    val IsString = symbolOf[OpId.IsString]
    val IsBoolean = symbolOf[OpId.IsBoolean]
    val IsSymbol = symbolOf[OpId.IsSymbol]
    val Negate = symbolOf[OpId.Negate]
    val Abs = symbolOf[OpId.Abs]
    val NumberOfLeadingZeros = symbolOf[OpId.NumberOfLeadingZeros]
    val Floor = symbolOf[OpId.Floor]
    val Ceil = symbolOf[OpId.Ceil]
    val Round = symbolOf[OpId.Round]
    val Sin = symbolOf[OpId.Sin]
    val Cos = symbolOf[OpId.Cos]
    val Tan = symbolOf[OpId.Tan]
    val Sqrt = symbolOf[OpId.Sqrt]
    val Log = symbolOf[OpId.Log]
    val Log10 = symbolOf[OpId.Log10]
    val Reverse = symbolOf[OpId.Reverse]
    val ! = symbolOf[OpId.!]
    val Require = symbolOf[OpId.Require]
    val ITE = symbolOf[OpId.ITE]
    val IsNonLiteral = symbolOf[OpId.IsNonLiteral]
    val ==> = symbolOf[OpId.==>]
    val + = symbolOf[OpId.+]
    val - = symbolOf[OpId.-]
    val * = symbolOf[OpId.*]
    val / = symbolOf[OpId./]
    val % = symbolOf[OpId.%]
    val < = symbolOf[OpId.<]
    val > = symbolOf[OpId.>]
    val <= = symbolOf[OpId.<=]
    val >= = symbolOf[OpId.>=]
    val == = symbolOf[OpId.==]
    val != = symbolOf[OpId.!=]
    val && = symbolOf[OpId.&&]
    val || = symbolOf[OpId.||]
    val Pow = symbolOf[OpId.Pow]
    val Min = symbolOf[OpId.Min]
    val Max = symbolOf[OpId.Max]
    val Substring = symbolOf[OpId.Substring]
    val CharAt = symbolOf[OpId.CharAt]
    val Length = symbolOf[OpId.Length]
  }

  ////////////////////////////////////////////////////////////////////
  // Code thanks to Shapeless
  // https://github.com/milessabin/shapeless/blob/master/core/src/main/scala/shapeless/lazy.scala
  ////////////////////////////////////////////////////////////////////
  def setAnnotation(msg: String)(implicit annotatedSym : TypeSymbol): Unit = {
    import c.internal._
    import decorators._
    val tree0 =
      c.typecheck(
        q"""
          new _root_.scala.annotation.implicitNotFound("dummy")
        """,
        silent = false
      )

    class SubstMessage extends Transformer {
      val global = c.universe.asInstanceOf[scala.tools.nsc.Global]

      override def transform(tree: Tree): Tree = {
        super.transform {
          tree match {
            case Literal(Constant("dummy")) => Literal(Constant(msg))
            case t => t
          }
        }
      }
    }

    val tree = new SubstMessage().transform(tree0)

    annotatedSym.setAnnotations(Annotation(tree))
    ()
  }
  ////////////////////////////////////////////////////////////////////


  ////////////////////////////////////////////////////////////////////
  // Calc
  ////////////////////////////////////////////////////////////////////
  sealed trait Calc {
    type T
    val tpe : Type
  }

  sealed trait CalcType extends Calc
  object CalcType {
    sealed trait Char extends CalcType{type T = std.Char; val tpe = typeOf[std.Char]}
    sealed trait Int extends CalcType{type T = std.Int; val tpe = typeOf[std.Int]}
    sealed trait Long extends CalcType{type T = std.Long; val tpe = typeOf[std.Long]}
    sealed trait Float extends CalcType{type T = std.Float; val tpe = typeOf[std.Float]}
    sealed trait Double extends CalcType{type T = std.Double; val tpe = typeOf[std.Double]}
    sealed trait String extends CalcType{type T = std.String; val tpe = typeOf[std.String]}
    sealed trait Boolean extends CalcType{type T = std.Boolean; val tpe = typeOf[std.Boolean]}
    sealed trait Symbol extends CalcType{type T = std.Symbol; val tpe = typeOf[std.Symbol]}
    object Char extends Char
    object Int extends Int
    object Long extends Long
    object Float extends Float
    object Double extends Double
    object String extends String
    object Boolean extends Boolean
    object Symbol extends Symbol
  }

  sealed trait CalcVal extends Calc {
    val value : T
    val tree : Tree
  }
  object CalcVal {
    sealed trait Kind
    object Lit extends Kind
    object NLit extends Kind
    implicit val lift = Liftable[CalcVal] {p => p.tree}
    class Char(val value : std.Char, val tree : Tree) extends CalcVal with CalcType.Char
    object Char {
      def unapply(arg: Char) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    class Int(val value : std.Int, val tree : Tree) extends CalcVal with CalcType.Int
    object Int {
      def unapply(arg: Int) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    class Long(val value : std.Long, val tree : Tree) extends CalcVal with CalcType.Long
    object Long {
      def unapply(arg: Long) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    class Float(val value : std.Float, val tree : Tree) extends CalcVal with CalcType.Float
    object Float {
      def unapply(arg: Float) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    class Double(val value : std.Double, val tree : Tree) extends CalcVal with CalcType.Double
    object Double {
      def unapply(arg: Double) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    class String(val value : std.String, val tree : Tree) extends CalcVal with CalcType.String
    object String {
      def unapply(arg: String) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    class Boolean(val value : std.Boolean, val tree : Tree) extends CalcVal with CalcType.Boolean
    object Boolean {
      def unapply(arg: Boolean) : Option[(arg.T, Tree)] = Some((arg.value, arg.tree))
    }
    def apply[T](value : T, tree : Tree)(implicit unsupported : TypeSymbol, kind : Kind) = kind match {
      case Lit => CalcLit(value)
      case NLit => CalcNLit(value, tree)
    }
  }

  sealed trait CalcLit extends CalcVal {
    override val tpe = constantTypeOf(value)
  }

  object CalcLit {
    implicit val lift = Liftable[CalcLit] { p => p.tree }
    case class Char(override val value : std.Char) extends CalcVal.Char(value, Literal(Constant(value))) with CalcLit
    case class Int(override val value : std.Int) extends CalcVal.Int(value, Literal(Constant(value))) with CalcLit
    case class Long(override val value : std.Long) extends CalcVal.Long(value, Literal(Constant(value))) with CalcLit
    case class Float(override val value : std.Float) extends CalcVal.Float(value, Literal(Constant(value))) with CalcLit
    case class Double(override val value : std.Double) extends CalcVal.Double(value, Literal(Constant(value))) with CalcLit
    case class String(override val value : std.String) extends CalcVal.String(value, Literal(Constant(value))) with CalcLit
    case class Boolean(override val value : std.Boolean) extends CalcVal.Boolean(value, Literal(Constant(value))) with CalcLit
    def apply[T](t : T)(implicit unsupported : TypeSymbol) = t match {
      case t : std.Char => Char(t)
      case t : std.Int => Int(t)
      case t : std.Long => Long(t)
      case t : std.Float => Float(t)
      case t : std.Double => Double(t)
      case t : std.String => String(t)
      case t : std.Boolean => Boolean(t)
      case t : std.Symbol => String(t.name.toString)
      case _ => abort(s"Unsupported literal type: $t")
    }
    def unapply(arg: CalcLit) : Option[arg.T] = Some(arg.value)
  }

  sealed trait CalcTFType extends Calc
  object CalcTFType {
    object Char extends CalcTFType with CalcType.Char
    object Int extends CalcTFType with CalcType.Int
    object Long extends CalcTFType with CalcType.Long
    object Float extends CalcTFType with CalcType.Float
    object Double extends CalcTFType with CalcType.Double
    object String extends CalcTFType with CalcType.String
    object Boolean extends CalcTFType with CalcType.Boolean
  }

  sealed trait CalcNLit extends CalcVal
  object CalcNLit {
    implicit val lift = Liftable[CalcNLit] { p => p.tree }
    case class Char(override val tree : Tree) extends CalcVal.Char('\u0001', tree) with CalcNLit
    case class Int(override val tree : Tree) extends CalcVal.Int(1, tree) with CalcNLit
    case class Long(override val tree : Tree) extends CalcVal.Long(1L, tree) with CalcNLit
    case class Float(override val tree : Tree) extends CalcVal.Float(1.0f, tree) with CalcNLit
    case class Double(override val tree : Tree) extends CalcVal.Double(1.0, tree) with CalcNLit
    case class String(override val tree : Tree) extends CalcVal.String("1", tree) with CalcNLit
    case class Boolean(override val tree : Tree) extends CalcVal.Boolean(true, tree) with CalcNLit

    def apply[T](valueRef : T, tree : Tree)(implicit unsupported : TypeSymbol) : CalcNLit =
      CalcNLit(CalcLit(valueRef), tree)
    def apply(calcTypeRef : Calc, tree : Tree)(implicit unsupported : TypeSymbol) : CalcNLit = {
      calcTypeRef match {
        case (t : CalcType.Char) => Char(tree)
        case (t : CalcType.Int) => Int(tree)
        case (t : CalcType.Long) => Long(tree)
        case (t : CalcType.Float) => Float(tree)
        case (t : CalcType.Double) => Double(tree)
        case (t : CalcType.String) => String(tree)
        case (t : CalcType.Boolean) => Boolean(tree)
        case _ => abort("Unsupported type")
      }
    }
    def unapply(arg: CalcNLit) : Option[Tree] = Some(arg.tree)
  }
  case class CalcUnknown(t: Type) extends Calc {
    val tpe = t
  }
  object NonLiteralCalc {
    def unapply(tpe: Type): Option[CalcType] = tpe match {
      case TypeRef(_, sym, _) => sym match {
        case t if t == symbolOf[Char] => Some(CalcType.Char)
        case t if t == symbolOf[Int] => Some(CalcType.Int)
        case t if t == symbolOf[Long] => Some(CalcType.Long)
        case t if t == symbolOf[Float] => Some(CalcType.Float)
        case t if t == symbolOf[Double] => Some(CalcType.Double)
        case t if t == symbolOf[java.lang.String] => Some(CalcType.String)
        case t if t == symbolOf[Boolean] => Some(CalcType.Boolean)
        case t if t == symbolOf[scala.Symbol] => Some(CalcType.Symbol)
        case _ => None
      }
      case _ => None
    }
  }
  ////////////////////////////////////////////////////////////////////


  ////////////////////////////////////////////////////////////////////
  // Code thanks to Paul Phillips
  // https://github.com/paulp/psply/blob/master/src/main/scala/PsplyMacros.scala
  ////////////////////////////////////////////////////////////////////
  import scala.reflect.internal.SymbolTable

  /** Typecheck singleton types so as to obtain indirectly
    *  available known-at-compile-time values.
    */
  object TypeCalc {
    val verboseTraversal = false
    ////////////////////////////////////////////////////////////////////////
    // Calculates the integer value of Shapeless Nat
    ////////////////////////////////////////////////////////////////////////
    object NatCalc {
      def unapply(tp: Type)(implicit annotatedSym : TypeSymbol): Option[CalcLit.Int] = {
        tp match {
          case TypeRef(_, sym, args) if sym == symbolOf[shapeless.Succ[_]] =>
            args.head match {
              case NatCalc(t) => Some(CalcLit.Int(t.value+1))
              case _ => abort(s"Given Nat type is defective: $tp, raw: ${showRaw(tp)}")
            }
          case TypeRef(_, sym, _) if sym == symbolOf[shapeless._0] =>
            Some(CalcLit.Int(0))
          case TypeRef(pre, sym, Nil) =>
            unapply(sym.info asSeenFrom (pre, sym.owner))
          case _ =>
            None
        }
      }

    }
    ////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////
    // Calculates the TwoFace values
    ////////////////////////////////////////////////////////////////////////
    object TwoFaceCalc {
      def unappyArg(calcTFType : Option[CalcTFType], tfArgType : Type)(implicit annotatedSym : TypeSymbol): Option[Calc] = {
        TypeCalc.unapply(tfArgType) match {
          case Some(t : CalcLit) => Some(t)
          case _ => calcTFType
        }
      }

      def unapply(tp: Type)(implicit annotatedSym : TypeSymbol) : Option[Calc] = {
        tp match {
          case TypeRef(_, sym, args) =>
            val calcTFType = sym match {
              case t if t == symbolOf[TwoFaceAny._Char[_]] => Some(CalcTFType.Char)
              case t if t == symbolOf[TwoFaceAny._Int[_]] => Some(CalcTFType.Int)
              case t if t == symbolOf[TwoFaceAny._Long[_]] => Some(CalcTFType.Long)
              case t if t == symbolOf[TwoFaceAny._Float[_]] => Some(CalcTFType.Float)
              case t if t == symbolOf[TwoFaceAny._Double[_]] => Some(CalcTFType.Double)
              case t if t == symbolOf[TwoFaceAny._String[_]] => Some(CalcTFType.String)
              case t if t == symbolOf[TwoFaceAny._Boolean[_]] => Some(CalcTFType.Boolean)
              case _ => None
            }
            if (calcTFType.isDefined) unappyArg(calcTFType, args.head) else None
          case RefinedType(parents, scope) =>
            val calcTFType = parents.head.typeSymbol match {
              case t if t == symbolOf[TwoFaceAny.CharLike] => Some(CalcTFType.Char)
              case t if t == symbolOf[TwoFaceAny.IntLike] => Some(CalcTFType.Int)
              case t if t == symbolOf[TwoFaceAny.LongLike] => Some(CalcTFType.Long)
              case t if t == symbolOf[TwoFaceAny.FloatLike] => Some(CalcTFType.Float)
              case t if t == symbolOf[TwoFaceAny.DoubleLike] => Some(CalcTFType.Double)
              case t if t == symbolOf[TwoFaceAny.StringLike] => Some(CalcTFType.String)
              case t if t == symbolOf[TwoFaceAny.BooleanLike] => Some(CalcTFType.Boolean)
              case _ => None
            }
            if (calcTFType.isDefined) {
              if (verboseTraversal) print(s"@@TwoFaceCalc@@\nTP: $tp\nRAW: + ${showRaw(tp)}")
              unappyArg(calcTFType, scope.head.asType.toType)
            } else None
          case _ => None
        }
      }
    }
    ////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////
    // Calculates the different Op wrappers by unapplying their argument.
    ////////////////////////////////////////////////////////////////////////
    object OpCastCalc {
      def unapply(tp: Type)(implicit annotatedSym : TypeSymbol): Option[Calc] = {
        tp match {
          case TypeRef(_, sym, args) =>
            sym match {
              case t if t == symbolOf[OpNat[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpChar[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpInt[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpLong[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpFloat[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpDouble[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpString[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpBoolean[_]] => Some(OpArgCalc.unapply(args.head))
              case t if t == symbolOf[OpSymbol[_]] => Some(OpArgCalc.unapply(args.head))
              case _ => None
            }
          case _ =>
            None
        }
      }
    }
    ////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////
    // Calculates an Op argument.
    ////////////////////////////////////////////////////////////////////////
    object OpArgCalc {
      def unapply(tp: Type)(implicit annotatedSym : TypeSymbol): Calc = {
        TypeCalc.unapply(tp) match {
          case Some(t : CalcVal) => t
          case Some(t : CalcType.Symbol) => CalcNLit(CalcType.String, q"valueOf[$tp].name")
          case Some(t : CalcTFType) => CalcNLit(t, q"valueOf[$tp].getValue")
          case Some(t : CalcType) => CalcNLit(t, q"valueOf[$tp]")
          case _ => CalcUnknown(tp)
        }
      }
    }
    ////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////
    // Calculates an Op
    ////////////////////////////////////////////////////////////////////////
    object OpCalc {
      def unapply(tp: Type)(implicit annotatedSym : TypeSymbol): Option[Calc] = {
        tp match {
          case TypeRef(_, sym, args) if sym == symbolOf[OpMacro[_,_,_,_]] =>
            if (verboseTraversal) print(s"@@OpCalc@@\nTP: $tp\nRAW: + ${showRaw(tp)}")
            val args = tp.typeArgs
            val funcType = args.head.typeSymbol.asType

            //If function is set/get variable we keep the original string,
            //otherwise we get the variable's value
            val aValue = OpArgCalc.unapply(args(1))
            val retVal = (funcType, aValue) match {
              case (funcTypes.IsNonLiteral, _) => //Looking for non literals
                aValue match {
                  case t : CalcLit => Some(CalcLit(false))
                  case _ => Some(CalcLit(true)) //non-literal type (e.g., Int, Long,...)
                }
              case (funcTypes.ITE, CalcLit.Boolean(cond)) => //Special control case: ITE (If-Then-Else)
                if (cond)
                  Some(OpArgCalc.unapply(args(2))) //true (then) part of the IF
                else
                  Some(OpArgCalc.unapply(args(3))) //false (else) part of the IF
              case (funcTypes.Arg, CalcLit.Int(argNum)) =>
                OpArgCalc.unapply(args(2)) match { //Checking the argument type
                  case t : CalcLit => Some(t) //Literal argument is just a literal
                  case _ => //Got a type, so returning argument name
                    TypeCalc.unapply(args(3)) match {
                      case Some(t: CalcType) =>
                        val term = TermName(s"arg$argNum")
                        Some(CalcNLit(t, q"$term"))
                      case _ =>
                        None
                    }
                }

              case _ => //regular cases
                val bValue = OpArgCalc.unapply(args(2))
                val cValue = OpArgCalc.unapply(args(3))
                (aValue, bValue, cValue) match {
                  case (a : CalcVal, b: CalcVal, c : Calc) =>
                    Some(opCalc(funcType, a, b, c))
                  case _ => None
                }
            }
            retVal
          case _ => None
        }
      }
    }
    ////////////////////////////////////////////////////////////////////////

    def unapply(tp: Type)(implicit annotatedSym : TypeSymbol): Option[Calc] = {
      val g = c.universe.asInstanceOf[SymbolTable]
      implicit def fixSymbolOps(sym: Symbol): g.Symbol = sym.asInstanceOf[g.Symbol]

      if (verboseTraversal) print(tp + " RAW " + showRaw(tp))
      tp match {
        ////////////////////////////////////////////////////////////////////////
        // Value cases
        ////////////////////////////////////////////////////////////////////////
        case OpCalc(t) => Some(t) // Operational Function
        case OpCastCalc(t) => Some(t) //Op Cast wrappers
        case TwoFaceCalc(t) => Some(t) //TwoFace values
        case NonLiteralCalc(t) => Some(t)// Non-literal values
        case NatCalc(t) => Some(t) //For Shapeless Nat
        case ConstantType(Constant(t)) => Some(CalcLit(t)) //Constant
        case SingletonSymbolType(s) => Some(CalcLit(s)) //Symbol constant
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        // Tree traversal
        ////////////////////////////////////////////////////////////////////////
        case tp @ ExistentialType(_, _) => unapply(tp.underlying)
        case TypeBounds(lo, hi) => unapply(hi)
        case RefinedType(parents, scope) =>
          parents.iterator map unapply collectFirst { case Some(x) => x }
        case NullaryMethodType(tpe) => unapply(tpe)
        case TypeRef(_, sym, _) if sym.isAliasType =>
          val tpDealias = tp.dealias
          if (tpDealias == tp)
            abort("Unable to dealias type: " + showRaw(tp))
          else
            unapply(tpDealias)
        case TypeRef(pre, sym, Nil) =>
          unapply(sym.info asSeenFrom (pre, sym.owner)) match {
            case Some(t : CalcVal) =>
              //Values (literal/non-literal) are OK and returned properly.
              Some(t)
            case _ =>
              //There can be cases, like in the following example, where we can extract a non-literal value.
              //  def foo2[W](w : TwoFace.Int[W])(implicit tfs : TwoFace.Int.Shell1[Negate, W, Int]) = -w+1
              //We want to calculate `-w+1`, even though we have not provided an complete implicit.
              //While returning `TwoFace.Int[Int](-w+1)` is possible in this case, we would rather reserve
              //the ability to have a literal return type, so `TwoFace.Int[Negate[W]+1](-w+1)` is returned.
              //So even if we can have a `Some(CalcType)` returning, we force it as `None`.
              None
          }
        case SingleType(pre, sym) =>
          unapply(sym.info asSeenFrom (pre, sym.owner))
        ////////////////////////////////////////////////////////////////////////

        case _ =>
//          print("Exhausted search at: " + showRaw(tp))
          None
      }
    }
  }
  ////////////////////////////////////////////////////////////////////


  def abort(msg: String)(implicit annotatedSym : TypeSymbol): Nothing = {
    setAnnotation(msg)
    c.abort(c.enclosingPosition, msg)
  }

  def constantTreeOf[T](t : T) : Tree = Literal(Constant(t))

  def constantTypeOf[T](t: T) : Type = c.internal.constantType(Constant(t))

  def genOpTreeLit[T](opTpe : Type, t: T)(implicit annotatedSym : TypeSymbol) : Tree = {
    val outTpe = constantTypeOf(t)
    val outTree = constantTreeOf(t)
    val outWideTpe = outTpe.widen
    val outTypeName = TypeName("Out" + wideTypeName(outTpe))
    val outWideLiteral = outTree
    q"""
      new $opTpe {
        type OutWide = $outWideTpe
        type Out = $outTpe
        type $outTypeName = $outTpe
        final val value: $outTpe = $outWideLiteral
        final val isLiteral = true
        final val valueWide: $outWideTpe = $outWideLiteral
      }
      """
  }

  def genOpTreeNat(opTpe : Type, t: Int) : Tree = {
    val outWideTpe = typeOf[Int]
    val outWideLiteral = constantTreeOf(t)
    val outTypeName = TypeName("OutNat")
    val outTpe = mkNatTpe(t)
    val outTree = q"new ${mkNatTpt(t)}"
    q"""
      new $opTpe {
        type OutWide = $outWideTpe
        type Out = $outTpe
        type $outTypeName = $outTpe
        final val value: $outTpe = $outTree
        final val isLiteral = true
        final val valueWide: $outWideTpe = $outWideLiteral
      }
      """
  }

  def genOpTreeSymbol(opTpe : Type, t: String) : Tree = {
    val outTpe = SingletonSymbolType(t)
    val outTree = mkSingletonSymbol(t)
    val outWideTpe = typeOf[std.Symbol]
    val outWideLiteral = outTree
    val outTypeName = TypeName("OutSymbol")
    q"""
      new $opTpe {
        type OutWide = $outWideTpe
        type Out = $outTpe
        type $outTypeName = $outTpe
        final val value: $outTpe = $outTree
        final val isLiteral = true
        final val valueWide: $outWideTpe = $outWideLiteral
      }
      """
  }

  def genOpTreeNLit(opTpe : Type, calc : CalcNLit)(implicit annotatedSym : TypeSymbol) : Tree = {
    val valueTree = calc.tree
    val outTpe = calc.tpe
    q"""
      new $opTpe {
        type OutWide = $outTpe
        type Out = $outTpe
        final val value: $outTpe = $valueTree
        final val isLiteral = false
        final val valueWide: $outTpe = $valueTree
      }
      """
  }

  def extractionFailed(tpe: Type)(implicit annotatedSym : TypeSymbol) = {
    val msg = s"Cannot extract value from $tpe\n" + "showRaw==> " + showRaw(tpe)
    abort(msg)
  }
  def extractionFailed(tree: Tree)(implicit annotatedSym : TypeSymbol) = {
    val msg = s"Cannot extract value from $tree\n" + "showRaw==> " + showRaw(tree)
    abort(msg)
  }

  def extractSingletonValue(tpe: Type)(implicit annotatedSym : TypeSymbol): Calc =
    TypeCalc.OpArgCalc.unapply(tpe)

  def extractValueFromOpTree(opTree : c.Tree)(implicit annotatedSym : TypeSymbol) : CalcVal = {
    def outFindCond(elem : c.Tree) : Boolean = elem match {
      case q"final val value : $valueTpe = $valueTree" => true
      case _ => false
    }
    def getOut(opClsBlk : List[c.Tree]) : CalcVal = opClsBlk.find(outFindCond) match {
      case Some(q"final val value : $valueTpe = $valueTree") =>
        valueTree match {
          case Literal(Constant(t)) => CalcLit(t)
          case _ => valueTpe match {
            case NonLiteralCalc(t) => CalcNLit(t, q"$valueTree")
            case _ => extractionFailed(opTree)
          }
        }
      case _ => extractionFailed(opTree)
    }

    opTree match {
      case q"""{
        $mods class $tpname[..$tparams] $ctorMods(...$paramss) extends ..$parents { $self => ..$opClsBlk }
        $expr(...$exprss)
      }""" => getOut(opClsBlk)
      case _ => extractionFailed(opTree)
    }
  }

  def extractValueFromNumTree(numValueTree : c.Tree)(implicit annotatedSym : TypeSymbol) : CalcVal = {
    val typedTree = c.typecheck(numValueTree)
    extractSingletonValue(typedTree.tpe) match {
      case t : CalcLit => t
      case t : CalcType => CalcNLit(t, numValueTree)
      case _ => extractionFailed(typedTree.tpe)
    }
  }

  def extractValueFromTwoFaceTree(tfTree : c.Tree)(implicit annotatedSym : TypeSymbol) : CalcVal = {
    val typedTree = c.typecheck(tfTree)
    extractSingletonValue(typedTree.tpe) match {
      case t : CalcLit => t
      case t : CalcType => CalcNLit(t, q"$tfTree.getValue")
      case _ => extractionFailed(typedTree.tpe)
    }
  }

  def wideTypeName(tpe : Type) : String = tpe.widen.typeSymbol.name.toString
  ///////////////////////////////////////////////////////////////////////////////////////////
  // Three operands (Generic)
  ///////////////////////////////////////////////////////////////////////////////////////////
  def materializeOpGen[F](implicit ev0: c.WeakTypeTag[F]): MaterializeOpAuxGen =
    new MaterializeOpAuxGen(weakTypeOf[F])

  def opCalc(funcType : TypeSymbol, a : CalcVal, b : CalcVal, c : Calc)(implicit annotatedSym : TypeSymbol) : CalcVal = {
    def unsupported() = abort(s"Unsupported $funcType[$a, $b, $c]")

    //The output val is literal if all arguments are literal. Otherwise, it is non-literal.
    implicit val cvKind : CalcVal.Kind = (a, b, c) match {
      case (_ : CalcLit, _ : CalcLit, _ : CalcLit) => CalcVal.Lit
      case _ => CalcVal.NLit
    }

    def AcceptNonLiteral = a
    def Id = a
    def ToNat = ToInt //Same handling, but also has a special case to handle this in MaterializeOpAuxGen
    def ToChar = a match {
      case CalcVal.Char(t, tt) => CalcVal(t.toChar, q"$tt.toChar")
      case CalcVal.Int(t, tt) => CalcVal(t.toChar, q"$tt.toChar")
      case CalcVal.Long(t, tt) => CalcVal(t.toChar, q"$tt.toChar")
      case CalcVal.Float(t, tt) => CalcVal(t.toChar, q"$tt.toChar")
      case CalcVal.Double(t, tt) => CalcVal(t.toChar, q"$tt.toChar")
      case _ => unsupported()
    }
    def ToInt = a match {
      case CalcVal.Char(t, tt) => CalcVal(t.toInt, q"$tt.toInt")
      case CalcVal.Int(t, tt) => CalcVal(t.toInt, q"$tt.toInt")
      case CalcVal.Long(t, tt) => CalcVal(t.toInt, q"$tt.toInt")
      case CalcVal.Float(t, tt) => CalcVal(t.toInt, q"$tt.toInt")
      case CalcVal.Double(t, tt) => CalcVal(t.toInt, q"$tt.toInt")
      case CalcVal.String(t, tt) => CalcVal(t.toInt, q"$tt.toInt")
      case _ => unsupported()
    }
    def ToLong = a match {
      case CalcVal.Char(t, tt) => CalcVal(t.toLong, q"$tt.toLong")
      case CalcVal.Int(t, tt) => CalcVal(t.toLong, q"$tt.toLong")
      case CalcVal.Long(t, tt) => CalcVal(t.toLong, q"$tt.toLong")
      case CalcVal.Float(t, tt) => CalcVal(t.toLong, q"$tt.toLong")
      case CalcVal.Double(t, tt) => CalcVal(t.toLong, q"$tt.toLong")
      case CalcVal.String(t, tt) => CalcVal(t.toLong, q"$tt.toLong")
      case _ => unsupported()
    }
    def ToFloat = a match {
      case CalcVal.Char(t, tt) => CalcVal(t.toFloat, q"$tt.toFloat")
      case CalcVal.Int(t, tt) => CalcVal(t.toFloat, q"$tt.toFloat")
      case CalcVal.Long(t, tt) => CalcVal(t.toFloat, q"$tt.toFloat")
      case CalcVal.Float(t, tt) => CalcVal(t.toFloat, q"$tt.toFloat")
      case CalcVal.Double(t, tt) => CalcVal(t.toFloat, q"$tt.toFloat")
      case CalcVal.String(t, tt) => CalcVal(t.toFloat, q"$tt.toFloat")
      case _ => unsupported()
    }
    def ToDouble = a match {
      case CalcVal.Char(t, tt) => CalcVal(t.toDouble, q"$tt.toDouble")
      case CalcVal.Int(t, tt) => CalcVal(t.toDouble, q"$tt.toDouble")
      case CalcVal.Long(t, tt) => CalcVal(t.toDouble, q"$tt.toDouble")
      case CalcVal.Float(t, tt) => CalcVal(t.toDouble, q"$tt.toDouble")
      case CalcVal.Double(t, tt) => CalcVal(t.toDouble, q"$tt.toDouble")
      case CalcVal.String(t, tt) => CalcVal(t.toDouble, q"$tt.toDouble")
      case _ => unsupported()
    }
    def ToString = a match {
      case CalcVal.Char(t, tt) => CalcVal(t.toString, q"$tt.toString")
      case CalcVal.Int(t, tt) => CalcVal(t.toString, q"$tt.toString")
      case CalcVal.Long(t, tt) => CalcVal(t.toString, q"$tt.toString")
      case CalcVal.Float(t, tt) => CalcVal(t.toString, q"$tt.toString")
      case CalcVal.Double(t, tt) => CalcVal(t.toString, q"$tt.toString")
      case CalcVal.String(t, tt) => CalcVal(t.toString, q"$tt.toString")
      case CalcVal.Boolean(t, tt) => CalcVal(t.toString, q"$tt.toString")
    }
    def ToSymbol = ToString //Same handling, but has also has a special case in MaterializeOpAuxGen
    def IsNat = a match {
      case CalcLit.Int(t) => CalcLit(t >= 0)
      case _ => CalcLit(false)
    }
    def IsChar = a match {
      case t : CalcType.Char => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsInt = a match {
      case t : CalcType.Int => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsLong = a match {
      case t : CalcType.Long => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsFloat = a match {
      case t : CalcType.Float => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsDouble = a match {
      case t : CalcType.Double => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsString = a match {
      case t : CalcType.String => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsBoolean = a match {
      case t : CalcType.Boolean => CalcLit(true)
      case _ => CalcLit(false)
    }
    def IsSymbol = IsString
    def Negate = a match {
      case CalcVal.Char(t, tt) => CalcVal(-t, q"-$tt")
      case CalcVal.Int(t, tt) => CalcVal(-t, q"-$tt")
      case CalcVal.Long(t, tt) => CalcVal(-t, q"-$tt")
      case CalcVal.Float(t, tt) => CalcVal(-t, q"-$tt")
      case CalcVal.Double(t, tt) => CalcVal(-t, q"-$tt")
      case _ => unsupported()
    }
    def Abs = a match {
      case CalcVal.Int(t, tt) => CalcVal(math.abs(t), q"_root_.scala.math.abs($tt)")
      case CalcVal.Long(t, tt) => CalcVal(math.abs(t), q"_root_.scala.math.abs($tt)")
      case CalcVal.Float(t, tt) => CalcVal(math.abs(t), q"_root_.scala.math.abs($tt)")
      case CalcVal.Double(t, tt) => CalcVal(math.abs(t), q"_root_.scala.math.abs($tt)")
      case _ => unsupported()
    }
    def NumberOfLeadingZeros = a match {
      case CalcVal.Int(t, tt) => CalcVal(nlz(t), q"_root_.singleton.ops.impl.nlz($tt)")
      case CalcVal.Long(t, tt) => CalcVal(nlz(t), q"_root_.singleton.ops.impl.nlz($tt)")
      case _ => unsupported()
    }
    def Floor = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.floor(t.toDouble), q"_root_.scala.math.floor($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.floor(t), q"_root_.scala.math.floor($tt)")
      case _ => unsupported()
    }
    def Ceil = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.ceil(t.toDouble), q"_root_.scala.math.ceil($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.ceil(t), q"_root_.scala.math.ceil($tt)")
      case _ => unsupported()
    }
    def Round = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.round(t), q"_root_.scala.math.round($tt)")
      case CalcVal.Double(t, tt) => CalcVal(math.round(t), q"_root_.scala.math.round($tt)")
      case _ => unsupported()
    }
    def Sin = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.sin(t.toDouble), q"_root_.scala.math.sin($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.sin(t), q"_root_.scala.math.sin($tt)")
      case _ => unsupported()
    }
    def Cos = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.cos(t.toDouble), q"_root_.scala.math.cos($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.cos(t), q"_root_.scala.math.cos($tt)")
      case _ => unsupported()
    }
    def Tan = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.tan(t.toDouble), q"_root_.scala.math.tan($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.tan(t), q"_root_.scala.math.tan($tt)")
      case _ => unsupported()
    }
    def Sqrt = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.sqrt(t.toDouble), q"_root_.scala.math.sqrt($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.sqrt(t), q"_root_.scala.math.sqrt($tt)")
      case _ => unsupported()
    }
    def Log = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.log(t.toDouble), q"_root_.scala.math.log($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.log(t), q"_root_.scala.math.log($tt)")
      case _ => unsupported()
    }
    def Log10 = a match {
      case CalcVal.Float(t, tt) => CalcVal(math.log10(t.toDouble), q"_root_.scala.math.log10($tt.toDouble)")
      case CalcVal.Double(t, tt) => CalcVal(math.log10(t), q"_root_.scala.math.log10($tt)")
      case _ => unsupported()
    }
    def Reverse = a match {
      case CalcVal.String(t, tt) => CalcVal(t.reverse, q"$tt.reverse")
      case _ => unsupported()
    }
    def Not = a match {
      case CalcVal.Boolean(t, tt) => CalcVal(!t, q"!$tt")
      case _ => unsupported()
    }
    def Require = a match {
      case CalcLit.Boolean(true) => CalcLit(true)
      case CalcLit.Boolean(false) => b match {
        case CalcLit.String(msg) => c match {
          case CalcUnknown(t) => //redirection of implicit not found annotation is required to the given symbol
            implicit val annotatedSym : TypeSymbol = t.typeSymbol.asType
            abort(msg)
          case _ => abort(msg)
        }
        //directly using the java lib `require` resulted in compiler crash, so we use wrapped require instead
        case CalcNLit.String(msg) => CalcNLit.Boolean(q"{_root_.singleton.ops.impl._require(false, $msg); false}")
        case _ => unsupported()
      }
      case CalcNLit.Boolean(cond) => b match {
        //directly using the java lib `require` resulted in compiler crash, so we use wrapped require instead
        case CalcVal.String(msg, msgt) => CalcNLit.Boolean(q"{_root_.singleton.ops.impl._require($cond, $msgt); true}")
        case _ => unsupported()
      }
      case _ => unsupported()
    }
    def ITE = (a, b, c) match {
      //Also has special case handling inside unapply
      case (CalcVal.Boolean(it,itt), CalcVal.Char(tt,ttt), CalcVal.Char(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case (CalcVal.Boolean(it,itt), CalcVal.Int(tt,ttt), CalcVal.Int(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case (CalcVal.Boolean(it,itt), CalcVal.Long(tt,ttt), CalcVal.Long(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case (CalcVal.Boolean(it,itt), CalcVal.Float(tt,ttt), CalcVal.Float(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case (CalcVal.Boolean(it,itt), CalcVal.Double(tt,ttt), CalcVal.Double(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case (CalcVal.Boolean(it,itt), CalcVal.String(tt,ttt), CalcVal.String(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case (CalcVal.Boolean(it,itt), CalcVal.Boolean(tt,ttt), CalcVal.Boolean(et,ett)) =>
        CalcVal(if(it) tt else et, q"if ($itt) $ttt else $ett")
      case _ => unsupported()
    }
    def Next = b
    def Plus = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case (CalcVal.String(at, att), CalcVal.String(bt, btt)) => CalcVal(at + bt, q"$att + $btt")
      case _ => unsupported()
    }
    def Minus = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at - bt, q"$att - $btt")
      case _ => unsupported()
    }
    def Mul = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at * bt, q"$att * $btt")
      case _ => unsupported()
    }
    def Div = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at / bt, q"$att / $btt")
      case _ => unsupported()
    }
    def Mod = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at % bt, q"$att % $btt")
      case _ => unsupported()
    }
    def Sml = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at < bt, q"$att < $btt")
      case _ => unsupported()
    }
    def Big = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at > bt, q"$att > $btt")
      case _ => unsupported()
    }
    def SmlEq = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at <= bt, q"$att <= $btt")
      case _ => unsupported()
    }
    def BigEq = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at >= bt, q"$att >= $btt")
      case _ => unsupported()
    }
    def Eq = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.String(at, att), CalcVal.String(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case (CalcVal.Boolean(at, att), CalcVal.Boolean(bt, btt)) => CalcVal(at == bt, q"$att == $btt")
      case _ => unsupported()
    }
    def Neq = (a, b) match {
      case (CalcVal.Char(at, att), CalcVal.Char(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Char(at, att), CalcVal.Int(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Char(at, att), CalcVal.Long(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Char(at, att), CalcVal.Float(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Char(at, att), CalcVal.Double(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Int(at, att), CalcVal.Char(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Int(at, att), CalcVal.Long(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Int(at, att), CalcVal.Float(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Int(at, att), CalcVal.Double(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Long(at, att), CalcVal.Char(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Long(at, att), CalcVal.Int(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Long(at, att), CalcVal.Float(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Long(at, att), CalcVal.Double(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Float(at, att), CalcVal.Char(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Float(at, att), CalcVal.Int(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Float(at, att), CalcVal.Long(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Double(at, att), CalcVal.Char(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Double(at, att), CalcVal.Int(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Double(at, att), CalcVal.Long(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.String(at, att), CalcVal.String(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case (CalcVal.Boolean(at, att), CalcVal.Boolean(bt, btt)) => CalcVal(at != bt, q"$att != $btt")
      case _ => unsupported()
    }
    def And = (a, b) match {
      case (CalcVal.Boolean(at, att), CalcVal.Boolean(bt, btt)) => CalcVal(at && bt, q"$att && $btt")
      case _ => unsupported()
    }
    def Or = (a, b) match {
      case (CalcVal.Boolean(at, att), CalcVal.Boolean(bt, btt)) => CalcVal(at || bt, q"$att || $btt")
      case _ => unsupported()
    }
    def Pow = (a, b) match {
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) =>
        CalcVal(math.pow(at.toDouble, bt.toDouble), q"_root_.scala.math.pow($att.toDouble, $btt.toDouble)")
      case (CalcVal.Float(at, att), CalcVal.Double(bt, btt)) =>
        CalcVal(math.pow(at.toDouble, bt.toDouble), q"_root_.scala.math.pow($att.toDouble, $btt.toDouble)")
      case (CalcVal.Double(at, att), CalcVal.Float(bt, btt)) =>
        CalcVal(math.pow(at.toDouble, bt.toDouble), q"_root_.scala.math.pow($att.toDouble, $btt.toDouble)")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) =>
        CalcVal(math.pow(at.toDouble, bt.toDouble), q"_root_.scala.math.pow($att.toDouble, $btt.toDouble)")
      case _ => unsupported()
    }
    def Min = (a, b) match {
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) =>
        CalcVal(math.min(at, bt), q"_root_.scala.math.min($att, $btt)")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) =>
        CalcVal(math.min(at, bt), q"_root_.scala.math.min($att, $btt)")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) =>
        CalcVal(math.min(at, bt), q"_root_.scala.math.min($att, $btt)")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) =>
        CalcVal(math.min(at, bt), q"_root_.scala.math.min($att, $btt)")
      case _ => unsupported()
    }
    def Max = (a, b) match {
      case (CalcVal.Int(at, att), CalcVal.Int(bt, btt)) =>
        CalcVal(math.max(at, bt), q"_root_.scala.math.max($att, $btt)")
      case (CalcVal.Long(at, att), CalcVal.Long(bt, btt)) =>
        CalcVal(math.max(at, bt), q"_root_.scala.math.max($att, $btt)")
      case (CalcVal.Float(at, att), CalcVal.Float(bt, btt)) =>
        CalcVal(math.max(at, bt), q"_root_.scala.math.max($att, $btt)")
      case (CalcVal.Double(at, att), CalcVal.Double(bt, btt)) =>
        CalcVal(math.max(at, bt), q"_root_.scala.math.max($att, $btt)")
      case _ => unsupported()
    }
    def Substring = (a, b) match {
      case (CalcLit.String(at), CalcLit.Int(bt)) => CalcLit(at.substring(bt))
      case (CalcVal.String(at, att), CalcVal.Int(bt, btt)) => CalcNLit.String(q"$att.substring($btt)")
      case _ => unsupported()
    }
    def CharAt = (a, b) match {
      case (CalcLit.String(at), CalcLit.Int(bt)) => CalcLit(at.charAt(bt))
      case (CalcVal.String(at, att), CalcVal.Int(bt, btt)) => CalcNLit.Char(q"$att.charAt($btt)")
      case _ => unsupported()
    }
    def Length = a match {
      case CalcVal.String(at, att) => CalcVal(at.length, q"$att.length")
      case _ => unsupported()
    }

    funcType match {
      case funcTypes.AcceptNonLiteral => AcceptNonLiteral
      case funcTypes.Id => Id
      case funcTypes.ToNat => ToNat
      case funcTypes.ToChar => ToChar
      case funcTypes.ToInt => ToInt
      case funcTypes.ToLong => ToLong
      case funcTypes.ToFloat => ToFloat
      case funcTypes.ToDouble => ToDouble
      case funcTypes.ToString => ToString
      case funcTypes.ToSymbol => ToSymbol
      case funcTypes.IsNat => IsNat
      case funcTypes.IsChar => IsChar
      case funcTypes.IsInt => IsInt
      case funcTypes.IsLong => IsLong
      case funcTypes.IsFloat => IsFloat
      case funcTypes.IsDouble => IsDouble
      case funcTypes.IsString => IsString
      case funcTypes.IsBoolean => IsBoolean
      case funcTypes.IsSymbol => IsSymbol
      case funcTypes.Negate => Negate
      case funcTypes.Abs => Abs
      case funcTypes.NumberOfLeadingZeros => NumberOfLeadingZeros
      case funcTypes.Floor => Floor
      case funcTypes.Ceil => Ceil
      case funcTypes.Round => Round
      case funcTypes.Sin => Sin
      case funcTypes.Cos => Cos
      case funcTypes.Tan => Tan
      case funcTypes.Sqrt => Sqrt
      case funcTypes.Log => Log
      case funcTypes.Log10 => Log10
      case funcTypes.Reverse => Reverse
      case funcTypes.! => Not
      case funcTypes.Require => Require
      case funcTypes.ITE => ITE
      case funcTypes.==> => Next
      case funcTypes.+ => Plus
      case funcTypes.- => Minus
      case funcTypes.* => Mul
      case funcTypes./ => Div
      case funcTypes.% => Mod
      case funcTypes.< => Sml
      case funcTypes.> => Big
      case funcTypes.<= => SmlEq
      case funcTypes.>= => BigEq
      case funcTypes.== => Eq
      case funcTypes.!= => Neq
      case funcTypes.&& => And
      case funcTypes.|| => Or
      case funcTypes.Pow => Pow
      case funcTypes.Min => Min
      case funcTypes.Max => Max
      case funcTypes.Substring => Substring
      case funcTypes.CharAt => CharAt
      case funcTypes.Length => Length
      case _ => abort(s"Unsupported $funcType[$a, $b, $c]")
    }
  }

  final class MaterializeOpAuxGen(opTpe: Type) {
    def usingFuncName : Tree = {
      implicit val annotatedSym : TypeSymbol = symbolOf[OpMacro[_,_,_,_]]
      val funcType = opTpe.typeArgs.head.typeSymbol.asType
      val opResult = extractSingletonValue(opTpe)

      val genTree = (funcType, opResult) match {
        case (funcTypes.ToNat, CalcLit.Int(t)) => genOpTreeNat(opTpe, t)
        case (funcTypes.ToSymbol, CalcLit.String(t)) => genOpTreeSymbol(opTpe, t)
        case (_, CalcLit(t)) => genOpTreeLit(opTpe, t)
        case (funcTypes.AcceptNonLiteral, t : CalcNLit) => genOpTreeNLit(opTpe, t)
        case (_, t: CalcNLit) =>
          abort("Calculation has returned a non-literal type/value.\nTo accept non-literal values, use `AcceptNonLiteral[T]`.")
        case _ => extractionFailed(opTpe)
      }

//      print(genTree)
      genTree
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////////


  ///////////////////////////////////////////////////////////////////////////////////////////
  // TwoFace Shell
  ///////////////////////////////////////////////////////////////////////////////////////////
  def TwoFaceShellMaterializer[Shell](implicit shell : c.WeakTypeTag[Shell])
  : TwoFaceShellMaterializer[Shell] = new TwoFaceShellMaterializer[Shell](weakTypeOf[Shell])

  final class TwoFaceShellMaterializer[Shell](shellTpe : Type) {
    def shell(shellAliasTpe : TypeSymbol) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = shellAliasTpe
      val funcApplyTpe = shellTpe.typeArgs(0)
      val funcArgsTpe = shellTpe.typeArgs(1)
      val (tfValueTree, tfName) = extractSingletonValue(funcArgsTpe) match {
        case (t: CalcVal) => (t.tree, t.tpe.widen.typeSymbol.name.toString)
        case _ => extractionFailed(shellTpe)
      }
      val tfTerm = TermName(tfName)
      val tfType = TypeName(tfName)
      val outTpe = extractSingletonValue(funcApplyTpe).tpe
      val paramVec = for (i <- 4 to shellTpe.typeArgs.length by 2)
        yield ValDef(Modifiers(Flag.PARAM),TermName(s"arg${(i-4)/2+1}"),tq"${shellTpe.typeArgs(i-1)}",EmptyTree)
      val paramTree = List(paramVec.toList)
      val genTree =
         q"""
           new $shellTpe {
             type Out = $outTpe
             type TF[T] = _root_.singleton.twoface.TwoFace.$tfType[T]
             def apply(...$paramTree) : _root_.singleton.twoface.TwoFace.$tfType[$outTpe] = {
               _root_.singleton.twoface.TwoFace.$tfTerm.create[$outTpe]($tfValueTree)
             }
           }
          """

//      print(showCode(genTree))
      genTree
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////////


  ///////////////////////////////////////////////////////////////////////////////////////////
  // TwoFace
  ///////////////////////////////////////////////////////////////////////////////////////////
  def TwoFaceMaterializer : TwoFaceMaterializer = new TwoFaceMaterializer

  final class TwoFaceMaterializer {
    def genTwoFace(calc : CalcVal) : c.Tree = {
      val outTpe = calc.tpe
      val outTree = calc.tree
      val tfName = wideTypeName(outTpe)
      val tfTerm = TermName(tfName)
      q"""
        _root_.singleton.twoface.TwoFace.$tfTerm.create[$outTpe]($outTree.asInstanceOf[$outTpe])
      """
    }
    def fromNumValue(numValueTree : c.Tree, tfSym : TypeSymbol) : c.Tree =  {
      implicit val annotatedSym : TypeSymbol = symbolOf[TwoFaceAny[_]]//not really used
      genTwoFace(extractValueFromNumTree(numValueTree))
    }
    def toNumValue[Out](tfTree : c.Tree, tfSym : TypeSymbol, tTpe : Type) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = tfSym
      val calc = extractValueFromTwoFaceTree(tfTree)
      val outTpe = calc.tpe
      val outTree = calc.tree
      val genTree =
        q"""
          $outTree.asInstanceOf[$tTpe]
        """
//      print(genTree)
      genTree
    }
    def equal(tTree : c.Tree, rTree : c.Tree) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = symbolOf[TwoFaceAny[_]]//not really used
      val tCalc = extractValueFromTwoFaceTree(tTree)
      val rCalc = extractValueFromNumTree(rTree)
      val outCalc = opCalc(funcTypes.==, tCalc, rCalc, CalcLit(0))
      val genTree = genTwoFace(outCalc)
      //      print(genTree)
      genTree
    }
    def nequal(tTree : c.Tree, rTree : c.Tree) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = symbolOf[TwoFaceAny[_]]//not really used
      val tCalc = extractValueFromTwoFaceTree(tTree)
      val rCalc = extractValueFromNumTree(rTree)
      val outCalc = opCalc(funcTypes.!=, tCalc, rCalc, CalcLit(0))
      genTwoFace(outCalc)
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////////


  ///////////////////////////////////////////////////////////////////////////////////////////
  // Checked TwoFace
  ///////////////////////////////////////////////////////////////////////////////////////////
  def CheckedImplMaterializer[PrimChk, SecChk, Cond, Msg](implicit primChk : c.WeakTypeTag[PrimChk], secChk : c.WeakTypeTag[SecChk], cond : c.WeakTypeTag[Cond], msg : c.WeakTypeTag[Msg]) :
  CheckedImplMaterializer[PrimChk, SecChk, Cond, Msg] = new CheckedImplMaterializer[PrimChk, SecChk, Cond, Msg](symbolOf[PrimChk], symbolOf[SecChk], weakTypeOf[Cond], weakTypeOf[Msg])

  final class CheckedImplMaterializer[PrimChk, SecChk, Cond, Msg](primChkSym : TypeSymbol, secChkSym : TypeSymbol, condTpe : Type, msgTpe : Type) {
    def newChecked(paramNum : Int, calc : CalcVal, chkArgTpe : Type)(implicit annotatedSym : TypeSymbol) : c.Tree = {
      val outTpe = calc.tpe
      val outTree = calc.tree
      val outTpeWide = outTpe.widen
      val fixedCondTpe = condTpe.substituteTypes(List(condTpe.typeArgs.head.typeSymbol), List(outTpe))
      val fixedMsgTpe = msgTpe.substituteTypes(List(msgTpe.typeArgs.head.typeSymbol), List(outTpe))

      val condCalc = extractSingletonValue(fixedCondTpe) match {
        case t : CalcVal => t
        case _ => extractionFailed(fixedCondTpe)
      }

      val msgCalc = extractSingletonValue(fixedMsgTpe) match {
        case t : CalcVal => t
        case _ => extractionFailed(fixedMsgTpe)
      }

      val reqCalc = opCalc(funcTypes.Require, condCalc, msgCalc, CalcLit(0))
      paramNum match {
        case 0 =>
          q"""
             (new $secChkSym[$chkArgTpe]($outTree.asInstanceOf[$outTpe])).asInstanceOf[$primChkSym[$chkArgTpe]]
           """
//        case 1 => q"new $chkSym[$outTpe,$paramTpe]($valueTree)"
        case _ =>
          abort("Unsupported number of parameters")
      }
    }
    def newChecked(paramNum : Int, calc : CalcVal)(implicit annotatedSym : TypeSymbol) : c.Tree =
      newChecked(paramNum, calc, calc.tpe)
    def fromOpApply(paramNum : Int, opTree : c.Tree) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = primChkSym
      print("======>fromOpApply")
      val numValueCalc = extractValueFromOpTree(opTree)
      val genTree = newChecked(0, numValueCalc)
            print(genTree)
      print("<======fromOpApply")
      genTree
    }
    def fromOpImpl(paramNum : Int, opTree : c.Tree, tTpe : Type) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = primChkSym
      print("======>fromOpImpl")
      val numValueCalc = extractValueFromOpTree(opTree)
      val genTree = newChecked(0, numValueCalc, tTpe)
      print("<======fromOpImpl")
      genTree
    }
    def fromNumValue(paramNum : Int, numValueTree : c.Tree) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = primChkSym
      print("======>fromNumValue")
      val numValueCalc = extractValueFromNumTree(numValueTree)
      val genTree = newChecked(0, numValueCalc)
      print(genTree)
      print("<======fromNumValue")
      genTree
    }
    def fromTF(paramNum : Int, tfTree : c.Tree) : c.Tree = {
      implicit val annotatedSym : TypeSymbol = primChkSym
      print("======>fromTF")
      val tfValueCalc = extractValueFromTwoFaceTree(tfTree)
      val genTree = newChecked(0, tfValueCalc)
            print(genTree)
      print("<======fromTF")
      genTree
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////////


  //copied from Shapeless
  import scala.annotation.tailrec
  def mkNatTpt(i: Int): Tree = {
    val succSym = typeOf[shapeless.Succ[_]].typeConstructor.typeSymbol
    val _0Sym = typeOf[shapeless._0].typeSymbol

    @tailrec
    def loop(i: Int, acc: Tree): Tree = {
      if (i == 0) acc
      else loop(i - 1, AppliedTypeTree(Ident(succSym), List(acc)))
    }

    loop(i, Ident(_0Sym))
  }

  //copied from Shapeless
  def mkNatTpe(i: Int): Type = {
    val succTpe = typeOf[shapeless.Succ[_]].typeConstructor
    val _0Tpe = typeOf[shapeless._0]

    @tailrec
    def loop(i: Int, acc: Type): Type = {
      if (i == 0) acc
      else loop(i - 1, appliedType(succTpe, acc))
    }

    loop(i, _0Tpe)
  }

  //copied from Shapeless
  def mkNatValue(i: Int): Tree =
  q""" new ${mkNatTpt(i)} """


  //copied from Shapeless
  val SymTpe = typeOf[std.Symbol]
  object SingletonSymbolType {
    val atatTpe = typeOf[@@[_,_]].typeConstructor
    val TaggedSym = typeOf[tag.Tagged[_]].typeConstructor.typeSymbol

    def unrefine(t: Type): Type =
      t.dealias match {
        case RefinedType(List(t), scope) if scope.isEmpty => unrefine(t)
        case t => t
      }

    def apply(s: String): Type = appliedType(atatTpe, List(SymTpe, c.internal.constantType(Constant(s))))

    def unapply(t: Type): Option[String] =
      unrefine(t).dealias match {
        case RefinedType(List(SymTpe, TypeRef(_, TaggedSym, List(ConstantType(Constant(s: String))))), _) => Some(s)
        case _ => None
      }
  }

  //copied from Shapeless
  def mkSingletonSymbol(s: String): Tree = {
    val sTpe = SingletonSymbolType(s)
    q"""_root_.std.Symbol($s).asInstanceOf[$sTpe]"""
  }
  ///////////////////////////////////////////////////////////////////////////////////////////
}
