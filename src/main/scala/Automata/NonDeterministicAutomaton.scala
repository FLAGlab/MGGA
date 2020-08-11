package Automata

import Visualization.JungTest

class NonDeterministicAutomaton(estados: Array[String], sigma: Array[String], q_i: String, q_f: Array[String], transicion: Array[Array[String]]) extends Automaton {
  private var automaton = collection.mutable.Map[(String, String), Array[String]]()

  for (q <- estados; s <- sigma)
    automaton += ((q, s) -> Array[String](""))

  for( q <- estados)
    automaton += ((q, "") -> Array[String](""))

  for (t <- transicion)
    automaton((t(0), t(1))) = automaton((t(0), t(1))).appended(t(2))


  private def recursiveRead(cadena: String, estadoIn: String, estadoFin: Array[String]): Boolean = {
    var pertenece = false
    var estadoAct = estadoIn
    if(!cadena.equals("") && !pertenece) {
      var s = cadena.split("")(0)
      println("+++++++++++++++++++++++++++++")
      for (state <- automaton((estadoAct, s))) {
        if (!state.equals("") && !pertenece) {
          var newText = cadena.substring(1)
          println(newText + " --- " + estadoAct + " --- " + s + " --- " + state)
          pertenece = recursiveRead(newText, state, estadoFin)
        }
      }
      if(!pertenece) {
        for (state2 <- automaton((estadoAct, ""))) {
          if (!state2.equals("") && !pertenece) {
            println(cadena + " --- " + estadoAct + " --- " + s + " --- " + state2)
            pertenece = recursiveRead(cadena, state2, estadoFin)
          }
        }
      }

      if(pertenece) {
        return true
      }
    }
    else {
      if(!pertenece) {
        for (state2 <- automaton((estadoAct, ""))) {
          if (!state2.equals("") && !pertenece) {
            pertenece = recursiveRead(cadena, state2, estadoFin)
          }
        }
      }
      
      for(state <- estadoFin)
        if (estadoAct.equals(state))
          pertenece = true
    }

    pertenece
  }

  def leer(cadena: String): Boolean = {
    recursiveRead(cadena, q_i, q_f)
  }

  def show(): Unit = {

    var i = 0
    var newTransition = Array[String]()
    while (i < transicion.length) {
      newTransition = newTransition.appended(transicion(i)(1) + "-" + transicion(i)(0) + "-" + transicion(i)(2))
      i = i + 1
    }

    JungTest.show(estados,newTransition,q_i,q_f, this)
  }
}
