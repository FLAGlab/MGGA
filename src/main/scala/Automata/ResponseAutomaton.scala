package Automata

import Visualization.JungTest

class ResponseAutomaton(estados: Array[String], sigma: Array[String], sigmaSalida: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]], h: (String, String) => String, g: (String) => String) extends Automaton {

  var automatonType = false
  private var automaton = collection.mutable.Map[(String, String), String]()

  for (q <- estados; s <- sigma) {
    automaton += ((q, s) -> "")
    if(g(q) != null)
      automatonType = true
  }
  
  for( q <- estados)
    automaton += ((q, "") -> "")

  for (t <- transicion)
    automaton((t(0), t(1))) = t(2)

  def leer(cadena: String): Boolean = {
    var pertenece = false
    var estadoAct = q_i
    var caracter = ""
    for (s <- cadena.split("")) {
      caracter = s
      if(automaton((estadoAct, caracter)).equals(""))
        if(!automaton((estadoAct, "")).equals(""))
          caracter = ""
        else
          return false

      println(estadoAct + " --- " + s + " --- " + automaton((estadoAct, caracter)))

      if(automatonType)
        println(g(automaton((estadoAct, caracter))))
      else
        println(h(estadoAct, caracter))

      estadoAct = automaton((estadoAct, caracter))
    }

    if (estadoAct.equals(q_f))
      pertenece = true

    pertenece
  }

  def show(): Unit = {

    var newState = Array[String]()
    var i = 0
    var newTransition = Array[String]()
    if(automatonType) {
      for(s <- estados) {
        newState = newState.appended(s+"/"+g(s))
      }
      while (i < transicion.length) {
        newTransition = newTransition.appended(transicion(i)(1) + "-" + transicion(i)(0)+"/"+g(transicion(i)(0)) + "-" + transicion(i)(2)+"/"+g(transicion(i)(2)))
        i = i + 1
      }

      JungTest.show(newState,newTransition,q_i,Array(q_f), this)
    }
    else {
      while (i < transicion.length) {
        newTransition = newTransition.appended(transicion(i)(1)+"/"+h(transicion(i)(0),transicion(i)(1)) + "-" + transicion(i)(0) + "-" + transicion(i)(2))
        i = i + 1
      }
      
      JungTest.show(estados,newTransition,q_i,Array(q_f), this)
    }
  }
}
