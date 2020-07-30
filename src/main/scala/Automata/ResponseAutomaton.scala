package Automata

class ResponseAutomaton(estados: Array[String], sigma: Array[String], sigmaSalida: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]], h: (String, String) => String, g: (String) => String) {

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

  def leerCadena(cadena: String): Boolean = {
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
}
