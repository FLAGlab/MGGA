object FistAutomata {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
  }

  def automata(estados: Array[String], sigma: Array[String], q_i: String, q_f: String, transicion: Array[Array[String]]):
  collection.mutable.Map[(String, String), String] = {
    var automaton = collection.mutable.Map[(String, String), String]()
    for(q <- estados; s <- sigma)
      automaton += ((q,s) -> "")

    for(t <-transicion) {
      automaton((t(0),t(1))) = t(2)
    }
    automaton
  }

  def leerCadena(automata: collection.mutable.Map[(String, String), String], cadena: String, estadoIn: String, estadoFin: String): Boolean ={
    var pertenece = false
    var estadoAct = estadoIn
    for(s <- cadena.split("")){
      println(estadoAct + "---" + s)
      estadoAct = automata((estadoAct,s))
      if(estadoAct.equals(""))
        return false
    }
    if(estadoAct.equals(estadoFin))
      pertenece = true

    pertenece
  }







  val estados: Array[String] = Array("A","B","C","D","E");
  val sigma: Array[String] = Array("a","b");
  val q_i: String = "A";
  val q_f: String = "D";
  val transicion: Array[Array[String]] = Array(Array("A","a","B"),Array("B","b","C"),
    Array("C","b","D"),Array("D","a","B"),Array("C","a","E"),Array("E","a","B"));
  val cadena = "abbabaabaabaabaabb";

  println(leerCadena(automata(estados, sigma, q_i, q_f, transicion), cadena, q_i: String, q_f))

}
