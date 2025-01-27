package MacrosTest

import scala.collection.mutable.{ListBuffer, Stack}
import scala.reflect.macros.blackbox
import scala.language.experimental.macros

object Examples {
  def printfff(format: String, params : Any*) : Unit = macro printf_impl

  def printf_impl(c : blackbox.Context)(format : c.Expr[String], params: c.Expr[Any]*) : c.Expr[Unit] = {
    import c.universe._

    val Literal(Constant(s_format:String)) = format.tree

    val evals = ListBuffer[ValDef]()
    def precompute(value: Tree, tpe: Type): Ident = {
      val freshName = TermName(c.fresh("eval$"))
      evals += ValDef(Modifiers(), freshName, TypeTree(tpe), value)
      Ident(freshName)
    }

    val paramsStack = Stack[Tree]((params map (_.tree)): _*)

    val refs = s_format.split("(?<=%[\\w%])|(?=%[\\w%])") map {
      case "%d" => precompute(paramsStack.pop, typeOf[Int])
      case "%s" => precompute(paramsStack.pop, typeOf[String])
      case "%%" => Literal(Constant("%"))
      case part => Literal(Constant(part))
    }

    val stats = evals ++ refs.map(ref => reify(print(c.Expr[Any](ref).splice)).tree)
    c.Expr[Unit](Block(stats.toList, Literal(Constant(()))))

  }
}
