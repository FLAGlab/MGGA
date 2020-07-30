package Automata

class DeterministicAutomaton(estados: Array[String], sigma: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]]) {
  private var automaton = collection.mutable.Map[(String, String), String]()

  for (q <- estados; s <- sigma)
    automaton += ((q, s) -> "")

  for (t <- transicion) {
    automaton((t(0), t(1))) = t(2)
  }

  def leerCadena(cadena: String): Boolean = {
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
}
