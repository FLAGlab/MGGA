package Automata

import scala.collection.mutable.Stack

class StackAutomatonNonDeterministic(estados: Array[String], sigma: Array[String], sigmaStack: Array[String], q_i: String, q_f: Array[String]) {

  private val PUSH = "0"
  private val POP = "1"
  private val SKIP = "2"
  private val CHANGE_TOP = "3"
  private val PUSH_ON = "4"
  private val IGNORE = "5"

  private var keyOperation = "key"
  private var stackOperation = collection.mutable.Map[(String, String, String), Array[String]]()
  private var realStackOperation = collection.mutable.Map[String, Array[String]]()
  private var automaton = collection.mutable.Map[(String, String), Array[String]]()

  private var myStack = Stack[String]()

  for (q <- estados; s <- sigma)
    automaton += ((q, s) -> Array[String](""))

  for( q <- estados)
    automaton += ((q, "") -> Array[String](""))

  private var sourceState = ""
  private var word = ""
  private var finalState = ""

  def recursiveRead(cadena: String, recursiveStack: Stack[String], q_i: String): Boolean = {
    var pertenece = false
    if(!cadena.equals("")) {
      var s = cadena.split("")(0)

      for(state <- automaton((q_i, ""))) {
        if (!state.equals("") && !pertenece) {
          var q_f = state

          var newText = cadena

          for(oper <- stackOperation(q_i, q_f, "")) {
            if(!pertenece) {
              var operation = realStackOperation(oper)
              println("RECURSIVO: " + recursiveStack + " OPERACION: " + operation(0) + " CADENA: " + newText)

              if(operation(0).equals(POP)) {
                if(myPop(operation(1),recursiveStack)) {
                  pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                }
              }
              else if(operation(0).equals(PUSH)) {
                if(myPush(operation(1),recursiveStack)) {
                  pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                }
              }
              else if(operation(0).equals(SKIP)) {
                if(mySkip(operation(1),recursiveStack)) {
                  pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                }
              }
              else if(operation(0).equals(CHANGE_TOP)) {
                if(myChangeTop(operation(1), operation(2),recursiveStack)) {
                  pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                }
              }
              else if(operation(0).equals(PUSH_ON)) {
                if(myPushOn(operation(1), operation(2),recursiveStack)) {
                  pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                }
              }
              else if(operation(0).equals(IGNORE)) {
                pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
              }
            }
          }
        }
      }
      
      if(!pertenece) {
        for(state <- automaton((q_i, s))) {
          if (!state.equals("") && !pertenece) {
            var newText = cadena.substring(1)
            var q_f = state
            for(oper <- stackOperation(q_i, q_f, s)) {
              if(!pertenece) {
                var operation = realStackOperation(oper)

                println("CADENA: " + cadena +" ESTADO:  " + q_i + " STACK: " + recursiveStack + " OPERACION: " + operation(0))

                if(operation(0).equals(POP)) {
                  if(myPop(operation(1),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                  }
                }
                else if(operation(0).equals(PUSH)) {
                  if(myPush(operation(1),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                  }
                }
                else if(operation(0).equals(SKIP)) {
                  if(mySkip(operation(1),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                  }
                }
                else if(operation(0).equals(CHANGE_TOP)) {
                  if(myChangeTop(operation(1), operation(2),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                  }
                }
                else if(operation(0).equals(PUSH_ON)) {
                  if(myPushOn(operation(1), operation(2),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                  }
                }
                else if(operation(0).equals(IGNORE)) {
                  pertenece = recursiveRead(newText, recursiveStack.clone, q_f)
                }
              }
            }
          }
        }
      }

      if(pertenece)
        return  true

    }
    else {
      if(!pertenece) {
        for(state <- automaton((q_i, ""))) {
          if (!state.equals("") && !pertenece) {
            var q_f = state

            var newText = cadena

            for(oper <- stackOperation(q_i, q_f, "")) {
              if(!pertenece) {
                var operation = realStackOperation(oper)
                println("RECURSIVO EVALUADO: " + recursiveStack + " OPERACION: " + operation(0) + " CADENA: " + newText)

                if(operation(0).equals(POP)) {
                  if(myPop(operation(1),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack, q_f)
                  }
                }
                else if(operation(0).equals(PUSH)) {
                  if(myPush(operation(1),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack, q_f)
                  }
                }
                else if(operation(0).equals(SKIP)) {
                  if(mySkip(operation(1),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack, q_f)
                  }
                }
                else if(operation(0).equals(CHANGE_TOP)) {
                  if(myChangeTop(operation(1), operation(2),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack, q_f)
                  }
                }
                else if(operation(0).equals(PUSH_ON)) {
                  if(myPushOn(operation(1), operation(2),recursiveStack)) {
                    pertenece = recursiveRead(newText, recursiveStack, q_f)
                  }
                }
                else if(operation(0).equals(IGNORE)) {
                  pertenece = recursiveRead(newText, recursiveStack, q_f)
                }
              }
            }
          }
        }
      }
      println("EVALUAAAAAAAAAAAAAAAAAA" + recursiveStack.isEmpty)
      for(state <- q_f)
        if (q_i.equals(state) && recursiveStack.isEmpty)
          pertenece = true
    }
    pertenece
  }

  def read(cadena: String): Boolean = {
    var pertenece  = false
    if(sourceState != "")
      throw new Exception("Check delta definition")
    else {
      pertenece = recursiveRead(cadena, myStack, q_i)
    }
    pertenece
  }

  def delta(sourceState: String, objectiveState: String, word: String): StackAutomatonNonDeterministic = {
    automaton((sourceState, word)) = automaton((sourceState, word)).appended(objectiveState)

    this.sourceState = sourceState
    this.word = word
    this.finalState = objectiveState

    this
  }

  def pop(element: String): Unit = {
    if(sourceState != "") {
      keyOperation = keyOperation + 1
      if(stackOperation.contains((sourceState, finalState, word))) {
        stackOperation((sourceState, finalState, word)) = stackOperation((sourceState, finalState, word)).appended(keyOperation)
      } else {
        stackOperation += ((sourceState, finalState, word) -> Array[String](keyOperation))
      }
      realStackOperation(keyOperation) = Array(POP, element)
    } else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def push(element: String): Unit = {
    if(sourceState != "") {
      keyOperation = keyOperation + 1
      if(stackOperation.contains((sourceState, finalState, word))) {
        stackOperation((sourceState, finalState, word)) = stackOperation((sourceState, finalState, word)).appended(keyOperation)
      } else {
        stackOperation += ((sourceState, finalState, word) -> Array[String](keyOperation))
      }
      realStackOperation(keyOperation) = Array(PUSH, element)
    } else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def skip(element: String): Unit = {
    if(sourceState != "") {
      keyOperation = keyOperation + 1
      if(stackOperation.contains((sourceState, finalState, word))) {
        stackOperation((sourceState, finalState, word)) = stackOperation((sourceState, finalState, word)).appended(keyOperation)
      } else {
        stackOperation += ((sourceState, finalState, word) -> Array[String](keyOperation))
      }
      realStackOperation(keyOperation) = Array(SKIP, element)
    } else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def changeTop(element1: String, element2: String): Unit = {
    if(sourceState != "") {
      keyOperation = keyOperation + 1
      if(stackOperation.contains((sourceState, finalState, word))) {
        stackOperation((sourceState, finalState, word)) = stackOperation((sourceState, finalState, word)).appended(keyOperation)
      } else {
        stackOperation += ((sourceState, finalState, word) -> Array[String](keyOperation))
      }
      realStackOperation(keyOperation) = Array(CHANGE_TOP, element1, element2)
    } else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def pushOn(element1: String, element2: String): Unit = {
    if(sourceState != "") {
      keyOperation = keyOperation + 1
      if(stackOperation.contains((sourceState, finalState, word))) {
        stackOperation((sourceState, finalState, word)) = stackOperation((sourceState, finalState, word)).appended(keyOperation)
      } else {
        stackOperation += ((sourceState, finalState, word) -> Array[String](keyOperation))
      }
      realStackOperation(keyOperation) = Array(PUSH_ON, element1, element2)
    } else
      throw new Exception("Check delta definition")

    sourceState = ""
  }

  def ignore(): Unit = {
    keyOperation = keyOperation + 1
    if(stackOperation.contains((sourceState, finalState, word))) {
      stackOperation((sourceState, finalState, word)) = stackOperation((sourceState, finalState, word)).appended(keyOperation)
    } else {
      stackOperation += ((sourceState, finalState, word) -> Array[String](keyOperation))
    }
    realStackOperation(keyOperation) = Array(IGNORE)

    sourceState = ""
  }

  private def myPop(element: String, rStack: Stack[String]): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element.split("")

    for(e <- splitElement) {
      if (rStack.length >= 1) {
        val word = rStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    if(currentWord.equals(element)) {
      makeOperation = true
    } else {
      splitElement = saveWord.split("")
      for(s <- splitElement) {
        rStack.push(s.toString)
      }
    }

    makeOperation
  }

  private def myPush(element: String, rStack: Stack[String]): Boolean = {
    // Revisar si element peretenece al alfabeto de la pila
    rStack.push(element)
    true
  }

  private def mySkip(element: String, rStack: Stack[String]): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element.split("")

    for(e <- splitElement) {
      if (rStack.length >= 1) {
        val word = rStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    splitElement = saveWord.split("")

    if(currentWord.equals(element)) {
      makeOperation = true

      for(s <- splitElement) {
        rStack.push(s.toString)
      }
    }
    else {
      for(s <- splitElement) {
        rStack.push(s.toString)
      }
    }

    makeOperation
  }
  private def myChangeTop(element1: String, element2: String, rStack: Stack[String]): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element1.split("")

    for(e <- splitElement) {
      if (rStack.length >= 1) {
        val word = rStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    if(currentWord.equals(element1)) {
      makeOperation = true

      splitElement = element2.split("")
      var cont  = splitElement.length
      while(cont > 0) {
        rStack.push(splitElement(cont-1))
        cont = cont - 1
      }
    }
    else {
      splitElement = saveWord.split("")
      for(s <- splitElement) {
        rStack.push(s.toString)
      }
    }

    makeOperation
  }
  private def myPushOn(element1: String, element2: String, rStack: Stack[String]): Boolean = {
    var makeOperation = false
    var currentWord = ""
    var saveWord = ""
    var splitElement = element1.split("")

    for(e <- splitElement) {
      if (rStack.length >= 1) {
        val word = rStack.pop()
        currentWord = currentWord + word
        saveWord = word + saveWord
      }
    }

    splitElement = saveWord.split("")

    if(currentWord.equals(element1)) {
      makeOperation = true

      for(s <- splitElement) {
        rStack.push(s.toString)
      }

      splitElement = element2.split("")
      var cont  = splitElement.length
      while(cont > 0) {
        rStack.push(splitElement(cont-1))
        cont = cont - 1
      }
    }
    else {
      for(s <- splitElement) {
        rStack.push(s.toString)
      }
    }

    makeOperation
  }
  private def myIgnore(): Boolean = {
    true
  }
}
