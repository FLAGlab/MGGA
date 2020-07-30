import Automata._

object mainTest {

  def main(args: Array[String]): Unit = {
    //--------------------------------------Deterministic----------------------------------------------------
    /*    
    val estados = Array("A","B","C","D","E");
    val sigma = Array("a","b");
    val q_i = "A";
    val q_f = "D";
    
    val transicion = Array(
      Array("A","a","B"),
      Array("B","b","C"),
      Array("C","b","D"),
      Array("D","a","B"),
      Array("C","a","E"),
      Array("E","a","B")
    );
    
    println(leerCadena(automaton(estados, sigma, q_i, q_f, transicion), cadena, q_i, q_f))
    
    */
    /*
    val estados = states("A","B","C","D","E")
    val sigma = alphabet("a","b")
    val q_i = "A"
    val q_f = "D"
    val transicion = transitions(
      d("A","a","B"),
      d("B","b","C"),
      d("C","b","D"),
      d("D","a","B"),
      d("C","a","E"),
      d("E","a","B"))
    
    //Defina un aut´omata de estados finitos que acepte el lenguaje a(bba|baa)∗bb
    val cadena = "abbabaabaabaabaabb"
    
    var det = new DeterministicAutomaton(estados, sigma, q_i, q_f, transicion)
    
    println(det.leerCadena(cadena))
    */
    //-------------------------------------------------------------------------------------------------------


    //------------------------------------------Stack--------------------------------------------------------
    /*
    val state = states("A")
    val sigma = alphabet("a","b")
    val r = alphabet("a","b")
    val q_i = "A"
    val q_f = Array("A")
    
    var stackAut = new StackAutomatonNonDeterministic(state,sigma, r, q_i, q_f)
    
    stackAut.delta("A","A","a").push("a")
    stackAut.delta("A","A","b").pop("a")
    
    println(s"Funcionaaaaa ${stackAut.read("abaaaabbbabb")}")
    println("Prueba Stack")
    */
     
    /*
    val state = states("A","B")
    val sigma = alphabet("a","b")
    val r = alphabet("a","b")
    val q_i = "A"
    val q_f = Array("B")

    var stackAut = new StackAutomatonNonDeterministic(state,sigma, r, q_i, q_f)

    stackAut.delta("A","A","a").push("a")
    stackAut.delta("A","A","b").push("b")
    stackAut.delta("A","B","").ignore()
    stackAut.delta("B","B","a").pop("a")
    stackAut.delta("B","B","b").pop("b")
    
    println(s"Funcionaaaaa ${stackAut.read("aabbaa")}")
    println("Prueba Stack")
    */

    /*
    val state = states("I","F","A","B")
    val sigma = alphabet("0","1","2","3","4","5","6","7","8","9","+","*","[","]","(",")","{","}")
    val r = alphabet("#","$","%")
    val q_i = "I"
    val q_f = Array("F")

    var M = new StackAutomatonNonDeterministic(state,sigma, r, q_i, q_f)

    M.delta("I","I","[").push("#")
    M.delta("I","I","(").push("$")
    M.delta("I","I","{").push("%")

    M.delta("F","F","]").pop("#")
    M.delta("F","F",")").pop("$")
    M.delta("F","F","}").pop("%")

    M.delta("A","A","[").push("#")
    M.delta("A","A","(").push("$")
    M.delta("A","A","{").push("%")

    M.delta("A","A","]").pop("#")
    M.delta("A","A",")").pop("$")
    M.delta("A","A","}").pop("%")


    M.delta("B","B","[").push("#")
    M.delta("B","B","(").push("$")
    M.delta("B","B","{").push("%")

    M.delta("B","B","]").pop("#")
    M.delta("B","B",")").pop("$")
    M.delta("B","B","}").pop("%")

    M.delta("I","A","0").ignore()
    M.delta("I","A","1").ignore()
    M.delta("I","A","2").ignore()
    M.delta("I","A","3").ignore()
    M.delta("I","A","4").ignore()
    M.delta("I","A","5").ignore()
    M.delta("I","A","6").ignore()
    M.delta("I","A","7").ignore()
    M.delta("I","A","8").ignore()
    M.delta("I","A","9").ignore()

    M.delta("A","A","0").ignore()
    M.delta("A","A","1").ignore()
    M.delta("A","A","2").ignore()
    M.delta("A","A","3").ignore()
    M.delta("A","A","4").ignore()
    M.delta("A","A","5").ignore()
    M.delta("A","A","6").ignore()
    M.delta("A","A","7").ignore()
    M.delta("A","A","8").ignore()
    M.delta("A","A","9").ignore()

    M.delta("A","B","+").ignore()
    M.delta("A","B","*").ignore()

    M.delta("B","A","0").ignore()
    M.delta("B","A","1").ignore()
    M.delta("B","A","2").ignore()
    M.delta("B","A","3").ignore()
    M.delta("B","A","4").ignore()
    M.delta("B","A","5").ignore()
    M.delta("B","A","6").ignore()
    M.delta("B","A","7").ignore()
    M.delta("B","A","8").ignore()
    M.delta("B","A","9").ignore()
    
    M.delta("A","F","").ignore()
    
    println(s"Funcionaaaaa ${M.read("{3+2+(3)}")}")
    println("Prueba Stack")
    */

    
    val state = states("A","F","2","3","5","B","C","D","E","G","H","I","J","K","L")
    val sigma = alphabet("a","q","r","2","3","5")
    val r = alphabet("a")
    val q_i = "A"
    val q_f = Array("F")

    var M = new StackAutomatonNonDeterministic(state,sigma, r, q_i, q_f)

    M.delta("A","A","a").push("a")
    M.delta("A","F","").ignore()

    M.delta("A","2","2").ignore()
    M.delta("2","B","q").pop("a")
    M.delta("B","C","").pop("a")
    M.delta("C","2","").ignore()
    M.delta("2","F","").ignore()

    M.delta("A","3","3").ignore()
    M.delta("3","D","q").pop("a")
    M.delta("D","E","").pop("a")
    M.delta("E","G","").pop("a")
    M.delta("G","3","").ignore()
    M.delta("3","F","").ignore()

    M.delta("A","5","5").ignore()
    M.delta("5","H","q").pop("a")
    M.delta("H","I","").pop("a")
    M.delta("I","J","").pop("a")
    M.delta("J","K","").pop("a")
    M.delta("K","L","").pop("a")
    M.delta("L","5","").ignore()
    M.delta("5","F","").ignore()

    M.delta("F","F","r").pop("a")

    println(s"Funcionaaaaa ${M.read("aaaaaaaaaaaaaaaaaa5qqqrrr")}")
    println("Prueba Stack")
    
    //-------------------------------------------------------------------------------------------------------


    //-----------------------------------------Response------------------------------------------------------
    /*
    val estados = states("A","B","C","D","E")
    val sigma = alphabet("a","b")
    val sigmaSalida = alphabet("0","1","2","3","4","5")
    val q_i = "A"
    val q_f = "D"
    val transicion = transitions(
      d("A","a","B"),
      d("B","b","C"),
      d("C","b","D"),
      d("D","a","B"),
      d("C","a","E"),
      d("E","a","B")
    )
    
    def g(q: String): String = q match {
      case "A" => "0"
      case "B" => "1"
      case "C" => "2"
      case "D" => "3"
      case "E" => "4"
      case _ => null
    }
    
    def h(q: String, s: String): String = {
      var response: String = null
    
      if(q.equals("A")) {
        if(s.equals("a"))
          response = "0"
      }
      else if(q.equals("B")) {
        if (s.equals("b"))
          response = "1"
      }
      else if(q.equals("C")) {
        if (s.equals("b"))
          response = "2"
        else if (s.equals("a"))
          response = "5" 
      }
      else if(q.equals("D")) {
        if (s.equals("a"))
          response = "3"
      }
      else if(q.equals("E")) {
        if (s.equals("a"))
          response = "4"
      }
    
      response
    }
    
    def g_i(q: String): String = null
    def h_i(q: String, s: String): String = null
    
    val response = new ResponseAutomaton(estados, sigma, sigmaSalida, q_i, q_f, transicion, h, g_i)
        
    if(response.automatonType)
      println("Moore Response Automaton Test")
    else
      println("Mealy Response Automaton Test")
    
    val cadena = "abbabaabaabaabaabb";
    println(response.leerCadena(cadena))
    */
    //-------------------------------------------------------------------------------------------------------


    //-------------------------------------Non Deterministic-------------------------------------------------

    /*
    val estados = states("A","B","C","D","E","F","G","H","I","J","K","L")
    val sigma = alphabet("0","1")
    val q_i = "A"
    val q_f = Array("I")
    val transicion = transitions(
      d("A","0","A"), d("A","1","B"),
      d("A","0","E"),d("B","0","C"),
      d("B","1","D"),d("B","0","B"),
      d("E","0","E"),d("E","1","F"),
      d("E","0","G"),d("C","1","K"),
      d("D","0","D"),d("D","0","H"),
      d("F","0","L"),d("F","0","F"),
      d("G","1","J"),d("H","0","I"),
      d("K","0","I"),d("K","0","K"),
      d("L","1","I"),d("J","1","I"),
      d("J","0","J")
    )

    val cadena = "0000110"
    */
    /*
    val estados = states("A","B")
    val sigma = alphabet("a","b")
    val q_i = "A"
    val q_f = Array("B")
    val transicion = transitions(
      d("A","a","A"),
      d("A","b","A"),
      d("A","b","B"),
      d("B","b","B"),
    )
    
    val cadena = "aaaaaaaabbbaaaaaaaabbbbbbabbbbbb"
    */
    /*
    val estados = states("A","B","C","D","E")
    val sigma = alphabet("0","1")
    val q_i = "A"
    val q_f = Array("B","D")
    val transicion = transitions(
      d("A","","B"),
      d("A","","D"),
      d("B","1","B"),
      d("B","0","C"),
      d("C","1","C"),
      d("C","0","B"),
      d("D","1","E"),
      d("D","0","D"),
      d("E","1","D"),
      d("E","0","E"),
    )

    val cadena = "01101010"
    //(1*(01*01*)*)U(0*(10*10*)*)
    var nonDet = new NonDeterministicAutomaton(estados, sigma, q_i, q_f, transicion)

    println("Non Deterministic Automaton Test")
    println(nonDet.leer(cadena))
    */
    /*
    val estados = states("A","B","C")
    val sigma = alphabet("a","b","c","d")
    val q_i = "C"
    val q_f = Array("B")
    val transicion = transitions(
      d("C","c","A"),
      d("A","","B"),
      d("A","a","A"),
      d("A","d","A"),
      d("A","b","A"),
      d("B","a","B"),
      d("B","b","B"),
    )

    val cadena = "cabababdabababab"
    //(1*(01*01*)*)U(0*(10*10*)*)
    var nonDet = new NonDeterministicAutomaton(estados, sigma, q_i, q_f, transicion)

    println("Non Deterministic Automaton Test")
    println(nonDet.leer(cadena))
    */
    //-------------------------------------------------------------------------------------------------------
  }








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
