package MacrosTest

import scala.reflect.macros.blackbox
import scala.language.experimental.macros

object Macros {

  def hello: Unit = macro helloImpl

  def helloImpl(c: blackbox.Context): c.Expr[Unit] = {
    import c.universe._

    /*reify {
      println("hello!")
    }
    c.Expr {
      Apply(Ident(TermName("println")), List(Literal(Constant("hello!"))))
    }*/

    c.Expr(q"""println("hello!")""")
  }

  def hello2(s: String): Unit = macro hello2Impl

  def hello2Impl(c: blackbox.Context)(s: c.Expr[String]): c.Expr[Unit] = {
    import c.universe._

    /*reify {
      println(s"hello ${s.splice}!")
    }
    c.Expr {
      Apply(
        Ident(TermName("println")),
        List(
          Apply(
            Select(
              Apply(
                Select(
                  Literal(Constant("hello ")),
                  TermName("$plus")
                ),
                List(
                  s.tree
                )
              ),
              TermName("$plus")
            ),
            List(
              Literal(Constant("!"))
            )
          )
        )
      )
    }*/

    c.Expr(q"""println("hellooo " + ${s.tree} + "!")""")
  }


  /*
  def imprimir(p: String, s: Any*): Unit = macro imprimirImpl

  def imprimirImpl(c: blackbox.Context)(p: c.Expr[String], s: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._
    val tipo = "" + s.toString().split("\\(")(2).split("\\)")(0).replace("\"","")
    c.Expr(q"""println($p + " " + ${tipo} + "!")""")
  }
  
  def ((x: Any*)_|+|_(y: Any*)) : Unit = macro pruebaImpl
  
  def pruebaImpl(c: blackbox.Context)(x: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._
    
    c.Expr(q"""println("hellooo " + ${x.toString()} + "!")""")
  }*/
}
