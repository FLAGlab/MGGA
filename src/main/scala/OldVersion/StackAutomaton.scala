package OldVersion

import scala.collection.mutable.Stack

class StackAutomaton(estados: Array[String], sigma: Array[String], sigmaStack: Array[String], q_i: String, q_f: String) {

  private val PUSH = "0"
  private val POP = "1"
  private val SKIP = "2"
  private val CHANGE_TOP = "3"
  private val PUSH_ON = "4"
  private val IGNORE = "5"

  private var stackOperation = collection.mutable.Map[(String, String), Array[String]]()
  private var automaton = collection.mutable.Map[(String, String), String]()

  private var myStack = Stack[String]()

  for (q <- estados; s <- sigma)
    automaton += ((q, s) -> "")

  private var sourceState = ""
  private var word = ""

  /*
  def StackAutomaton(estados: Array[String], sigma: Array[String], q_i: String, q_f: String):
  collection.mutable.Map[(String, String), String] = {
    
    this.q_i = q_i 
    this.q_f = q_f
    
    for (q <- estados; s <- sigma)
      automaton += ((q, s) -> "")

    automaton
  }
  
   */

  /*
  private def leerCadena(automata: collection.mutable.Map[(String, String), String], cadena: String, estadoIn: String, estadoFin: String): Boolean = {
    var pertenece = false
    var estadoAct = estadoIn
    for (s <- cadena.split("")) {
      println(estadoAct + " --- " + s + " --- " + automata((estadoAct, s)))
      estadoAct = automata((estadoAct, s))
      if (estadoAct.equals(""))
        return false
    }
    if (estadoAct.equals(estadoFin))
      pertenece = true

    pertenece
  }
  
   */
  def read(cadena: String): Boolean = {
    var pertenece  = false
    if(sourceState != "")
      throw new Exception("Check delta definition")
    else {
      var actualState = q_i
      for(s <- cadena.split("")) {
        if(automaton((actualState, s)) == "") {
          return false
        } else {
          var operation = stackOperation(((actualState, s)))
          
          if(operation(0).equals(POP)) {
            if(!myPop(operation(1)))
              return false
          }
          else if(operation(0).equals(PUSH)) {
            if(!myPush(operation(1)))
            return false
          }
          else if(operation(0).equals(SKIP)) {
            if(!mySkip(operation(1)))
              return false
          }
          else if(operation(0).equals(CHANGE_TOP)) {
            if(!myChangeTop(operation(1), operation(2)))
              return false
          }
          else if(operation(0).equals(PUSH_ON)) {
            if(!myPushOn(operation(1), operation(2)))
              return false
          }
          else if(operation(0).equals(IGNORE)) {
            myIgnore()
          }
          actualState = automaton((actualState, s))
        }
      }
      if(actualState.equals(q_f) && myStack.isEmpty)
        pertenece = true
    }
    pertenece
  }

  def delta(sourceState: String, objectiveState: String, word: String): StackAutomaton = {
    automaton((sourceState, word)) = objectiveState

    this.sourceState = sourceState
    this.word = word

    this
  }

  def pop(element: String): Unit = {
    if(sourceState != "")
      stackOperation((sourceState, word)) = Array(POP, element)
    else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def push(element: String): Unit = {
    if(sourceState != "")
      stackOperation((sourceState, word)) = Array(PUSH, element)
    else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def skip(element: String): Unit = {
    if(sourceState != "")
      stackOperation((sourceState, word)) = Array(SKIP, element)
    else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def changeTop(element1: String, element2: String): Unit = {
    if(sourceState != "")
      stackOperation((sourceState, word)) = Array(CHANGE_TOP, element1, element2)
    else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def pushOn(element1: String, element2: String): Unit = {
    if(sourceState != "")
      stackOperation((sourceState, word)) = Array(PUSH_ON, element1, element2)
    else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def ignore(): Unit = {
    stackOperation((sourceState, "")) = Array(IGNORE)
  }

  private def myPop(element: String): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element.split("")

    for(e <- splitElement) {
      if (myStack.length >= 1) {
        val word = myStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    if(currentWord.equals(element)) {
      makeOperation = true
    } else {
      splitElement = saveWord.split("")
      for(s <- splitElement) {
        myStack.push(s.toString)
      }
    }

    makeOperation
  }

  private def myPush(element: String): Boolean = {
    // Revisar si element peretenece al alfabeto de la pila
    myStack.push(element)
    true
  }

  private def mySkip(element: String): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element.split("")

    for(e <- splitElement) {
      if (myStack.length >= 1) {
        val word = myStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    splitElement = saveWord.split("")

    if(currentWord.equals(element)) {
      makeOperation = true

      for(s <- splitElement) {
        myStack.push(s.toString)
      }
    }
    else {
      for(s <- splitElement) {
        myStack.push(s.toString)
      }
    }

    makeOperation
  }
  private def myChangeTop(element1: String, element2: String): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element1.split("")

    for(e <- splitElement) {
      if (myStack.length >= 1) {
        val word = myStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }


    if(currentWord.equals(element1)) {
      makeOperation = true

      splitElement = element2.split("")
      var cont  = splitElement.length
      while(cont > 0) {
        myStack.push(splitElement(cont-1))
        cont = cont - 1
      }
    }
    else {
      splitElement = saveWord.split("")
      for(s <- splitElement) {
        myStack.push(s.toString)
      }
    }

    makeOperation
  }
  private def myPushOn(element1: String, element2: String): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element1.split("")

    for(e <- splitElement) {
      if (myStack.length >= 1) {
        val word = myStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    splitElement = saveWord.split("")

    if(currentWord.equals(element1)) {
      makeOperation = true

      for(s <- splitElement) {
        myStack.push(s.toString)
      }

      splitElement = element2.split("")
      var cont  = splitElement.length
      while(cont > 0) {
        myStack.push(splitElement(cont-1))
        cont = cont - 1
      }
    }
    else {
      for(s <- splitElement) {
        myStack.push(s.toString)
      }
    }

    makeOperation
  }
  private def myIgnore(): Boolean = {
    true
  }
}
