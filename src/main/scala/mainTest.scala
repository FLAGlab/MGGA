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
      Array("A","a","B"),//aAB
      Array("B","b","C"),//bBC
      Array("C","b","D"),//bCD
      Array("D","a","B"),//aDB
      Array("C","a","E"),//aCE
      Array("E","a","B")//aEB
    );

    var M = new DeterministicAutomaton(estados,sigma,q_i,q_f,transicion)
    M.show()
    //println(M.leer("abbabaabbabaabb"))
    */

    /*
    val estados = Array("A","B","C","D","E")
    val sigma = Array("a","b")
    val q_i = "A"
    val q_f = "D"
    val transicion = Array(
      Array("A","a","B"),
      Array("B","b","C"),
      Array("C","b","D"),
      Array("D","a","B"),
      Array("C","a","E"),
      Array("E","a","B"))
    
    //Defina un autómata de estados finitos que acepte el lenguaje a(bba|baa)∗bb
    val cadena = "abbabaabaabaabaabb"
    
    var det = new DeterministicAutomaton(estados, sigma, q_i, q_f, transicion)
    det.show()
    println(det.leer(cadena))
    */
    //-------------------------------------------------------------------------------------------------------


    //------------------------------------------Stack--------------------------------------------------------
    /*
    val state = Array("A")
    val sigma = Array("a","b")
    val r = Array("a","b")
    val q_i = "A"
    val q_f = Array("A")
    
    var stackAut = new StackAutomatonNonDeterministic(state,sigma, r, q_i, q_f)
    
    stackAut.delta("A","A","a").push("a")
    stackAut.delta("A","A","b").pop("a")
    stackAut.show()
    //println(s"Funcionaaaaa ${stackAut.leer("abaaaabbbabb")}")
    println("Prueba Stack")
    */

    
    val state = Array("A","B")
    val sigma = Array("a","b")
    val r = Array("a","b")
    val q_i = "A"
    val q_f = Array("B")

    var stackAut = new StackAutomatonNonDeterministic(state,sigma, r, q_i, q_f)

    stackAut.delta("A","A","a").push("a")
    stackAut.delta("A","A","b").push("b")
    stackAut.delta("A","B","").ignore()
    stackAut.delta("B","B","a").pop("a")
    stackAut.delta("B","B","b").pop("b")
    stackAut.show()
    println(s"Funcionaaaaa ${stackAut.leer("aabbaa")}")
    println("Prueba Stack")
    

    /*
    val state = Array("I","F","A","B")
    val sigma = Array("0","1","2","3","4","5","6","7","8","9","+","*","[","]","(",")","{","}")
    val r = Array("#","$","%")
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
    M.show()
    println(s"Funcionaaaaa ${M.leer("{3+2+(3)}")}")
    println("Prueba Stack")
    */

    /*
    val state = Array("A","F","2","3","5","B","C","D","E","G","H","I","J","K","L")
    val sigma = Array("a","q","r","2","3","5")
    val r = Array("a")
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
    M.show()
    //println(s"Funcionaaaaa ${M.leer("aaaaaaaaaaaaaaaaaa5qqqrrr")}")
    println("Prueba Stack")
    */
    //-------------------------------------------------------------------------------------------------------


    //-----------------------------------------Response------------------------------------------------------
    /*
    val estados = Array("A","B","C","D","E")
    val sigma = Array("a","b")
    val sigmaSalida = Array("0","1","2","3","4","5")
    val q_i = "A"
    val q_f = "D"
    val transicion = Array(
      Array("A","a","B"),
      Array("B","b","C"),
      Array("C","b","D"),
      Array("D","a","B"),
      Array("C","a","E"),
      Array("E","a","B")
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
    
    val response = new ResponseAutomaton(estados, sigma, sigmaSalida, q_i, q_f, transicion, h_i, g)
        
    if(response.automatonType)
      println("Moore Response Automaton Test")
    else
      println("Mealy Response Automaton Test")
    
    val cadena = "abbabaabaabaabaabb";
    response.show()
    //println(response.leer(cadena))
    */
    //-------------------------------------------------------------------------------------------------------


    //-------------------------------------Non Deterministic-------------------------------------------------

    /*
    val estados = Array("A","B","C","D","E","F","G","H","I","J","K","L")
    val sigma = Array("0","1")
    val q_i = "A"
    val q_f = Array("I")
    val transicion = Array(
      Array("A","0","A"), Array("A","1","B"),
      Array("A","0","E"),Array("B","0","C"),
      Array("B","1","D"),Array("B","0","B"),
      Array("E","0","E"),Array("E","1","F"),
      Array("E","0","G"),Array("C","1","K"),
      Array("D","0","D"),Array("D","0","H"),
      Array("F","0","L"),Array("F","0","F"),
      Array("G","1","J"),Array("H","0","I"),
      Array("K","0","I"),Array("K","0","K"),
      Array("L","1","I"),Array("J","1","I"),
      Array("J","0","J")
    )

    val cadena = "0000110"
    var nonDet = new NonDeterministicAutomaton(estados, sigma, q_i, q_f, transicion)

    println("Non Deterministic Automaton Test")
    nonDet.show()
    println(nonDet.leer(cadena))
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
    val estados = Array("A","B","C")
    val sigma = Array("a","b","c","d")
    val q_i = "C"
    val q_f = Array("B")
    val transicion = Array(
      Array("C","c","A"),
      Array("A","","B"),
      Array("A","a","A"),
      Array("A","d","A"),
      Array("A","b","A"),
      Array("B","a","B"),
      Array("B","b","B"),
    )

    val cadena = "cabababdabababab"
    //(1*(01*01*)*)U(0*(10*10*)*)
    var nonDet = new NonDeterministicAutomaton(estados, sigma, q_i, q_f, transicion)
    nonDet.show()
    println("Non Deterministic Automaton Test")
    //println(nonDet.leer(cadena))
    */
    //-------------------------------------------------------------------------------------------------------
  }



}
