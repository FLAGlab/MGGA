package Automata

import Visualization.JungTest

class DeterministicAutomaton(estados: Array[String], sigma: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]]) extends Automaton {
  private var automaton = collection.mutable.Map[(String, String), String]()

  for (q <- estados; s <- sigma)
    automaton += ((q, s) -> "")

  for (t <- transicion) {
    automaton((t(0), t(1))) = t(2)
  }

  def leer(cadena: String): Boolean = {
    var pertenece = false
    var estadoAct = q_i
    for (s <- cadena.split("")) {
      println(estadoAct + " --- " + s + " --- " + automaton((estadoAct, s)))
      estadoAct = automaton((estadoAct, s))
      if (estadoAct.equals(""))
        return false
    }
    if (estadoAct.equals(q_f))
      pertenece = true

    pertenece
  }

  def show(): Unit = {
    
    var i = 0
    var newTransition = Array[String]()
    while (i < transicion.length) {
      newTransition = newTransition.appended(transicion(i)(1) + "-" + transicion(i)(0) + "-" + transicion(i)(2))
      i = i + 1
    }
    
    JungTest.show(estados,newTransition,q_i,Array(q_f), this)
  }
}
