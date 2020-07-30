package OldVersion

object ResponseAutomaton {
  
  var automatonType = false
  var response = collection.mutable.Map[(String, String), String]()

  def automaton(estados: Array[String], sigma: Array[String], sigmaSalida: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]], h: Array[Array[String]], g: Boolean):
  collection.mutable.Map[(String, String), String] = {
    var automaton = collection.mutable.Map[(String, String), String]()
    for (q <- estados; s <- sigma) {
      automaton += ((q, s) -> "")
      response += ((q, s) -> "   ¡ERROR!   ")
    }

    for (t <- transicion) {
      automaton((t(0), t(1))) = t(2)
    }

    //Mealy
    for (res <- h) {
      response((res(0), res(1))) = res(2)
    }
    automaton
  }

  def automaton(estados: Array[String], sigma: Array[String], sigmaSalida: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]], h: Boolean, g: Array[Array[String]]):
  collection.mutable.Map[(String, String), String] = {
    var automaton = collection.mutable.Map[(String, String), String]()
    for (q <- estados; s <- sigma) {
      automaton += ((q, s) -> "")
      response += ((q, "") -> "   ¡ERROR!   ")
    }

    for (t <- transicion) {
      automaton((t(0), t(1))) = t(2)
    }
    
    automatonType = true

    //Moore
    for (res <- g) {
      response((res(0), "")) = res(1)
    }
    
    automaton
  }

  def leerCadena(automata: collection.mutable.Map[(String, String), String], cadena: String, estadoIn: String, estadoFin: String): Boolean = {
    var pertenece = false
    var estadoAct = estadoIn
    for (s <- cadena.split("")) {
      println(estadoAct + " --- " + s + " --- " + automata((estadoAct, s)))
      
      if(automatonType)
        println(response((automata((estadoAct, s)), "")))
      else
        println(response((estadoAct, s)))
      
      estadoAct = automata((estadoAct, s))
      
      if (estadoAct.equals(""))
        return false
    }
    
    if (estadoAct.equals(estadoFin))
      pertenece = true

    pertenece
  }

  /*
  
  def main(args: Array[String]): Unit = {
    if(automatonType)
      println("Moore Response Automaton Test")
    else
      println("Mealy Response Automaton Test")
  }
  
  val estados: Array[String] = Array("A","B","C","D","E");
  val sigma: Array[String] = Array("a","b");
  val sigmaSalida: Array[String] = Array("0","1","2","3","4","5");
  val q_i: String = "A";
  val q_f: String = "D";
  val transicion: Array[Array[String]] = Array(Array("A","a","B"),Array("B","b","C"),
    Array("C","b","D"),Array("D","a","B"),Array("C","a","E"),Array("E","a","B"));
  val cadena = "abbabaabaabaabaabb";
  
  val g: Array[Array[String]] = Array(Array("A","0"),Array("B","1"),Array("C","2"),Array("D","3"),Array("E","4"));
  val h: Array[Array[String]] = Array(Array("A","a","0"),Array("B","b","1"),Array("C","b","2"),Array("C","a","5"),Array("D","a","3"),Array("E","a","4"));

  //Moore
  println(leerCadena(automaton(estados, sigma, sigmaSalida, q_i, q_f, transicion,false,g), cadena, q_i: String, q_f))
  
  //Mealy
  //println(leerCadena(automaton(estados, sigma, sigmaSalida, q_i, q_f, transicion,h,false), cadena, q_i: String, q_f))
  
  */
  
}
