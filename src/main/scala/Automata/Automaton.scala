package Automata

trait Automaton {
  
  def leer(str: String): Boolean;
  def show(): Unit;

  def states(states: String*): Array[String] = {
    var estado: Array[String] = Array()
    for(s <- states) {
      estado = estado.appended(s)
    }
    estado
  }

  def alphabet(sigma: String*): Array[String] = {
    var alfabeto: Array[String] = Array()
    for(s <- sigma) {
      alfabeto = alfabeto.appended(s)
    }
    alfabeto
  }

  def transitions(transitionValues: Array[String]*): Array[Array[String]] = {
    var transicion: Array[Array[String]] = Array()

    for(s <- transitionValues) {
      transicion = transicion.appended(s)
    }
    transicion
  }

  def d(sourceState: String, str: String, finalState: String): Array[String] = {
    Array(sourceState, str, finalState)
  }
}
