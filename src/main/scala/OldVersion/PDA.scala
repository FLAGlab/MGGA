package OldVersion

import scala.collection.mutable.Stack

object PDA {

  var myStack = Stack[String]();
  /**
  def main(args: Array[String]): Unit = {
    println("Holaaa!")
  }
   **/

  //Implementar transiciones con tupla [s,push/pop,skip,changeTop,pushOn], push(#),pop(#),skip(#),changeTop(#,%),pushOn(#,%)
  //Siempre hacer peek antes de un pop
  //changeTop(#,%) peek(#),pop(#),push(%)
  //pushOn(#,%) peek(#),push(%)
  // transition: Array[Array[String]]
  // ["A", "#,function(#)", "B"]
  def PDA(state: Array[String], sigma: Array[String], r: Array[String], q_i: String, q_f: Array[String],
          transition: Array[Array[String]]): Unit ={
  }

  def getTransition(qo: String, nTransition: String, qd: String): Array[Array[String]] ={
    val sVal = nTransition.split(".")(0)
    val sTransition = nTransition.split(".")(1)
    var myArray = Array[Array[String]]()
    myArray(0)(0) = qo
    myArray(0)(1) = "push"
    myArray(0)(2) = sVal
    myArray(1)(0) = "pop"
    myArray(1)(1) = qd

    if(sTransition.startsWith("pop(")){
      myArray(0)(1) = ""
      // Que hacer con myArray(1)(0) = "pop"
    }
    else if(sTransition.startsWith("push(")){
      myArray(1)(0) = ""
      // Que hacer con myArray(0)(1) = "push"
    }
    else if(sTransition.startsWith("skip(")){
      // Que hacer con myArray(1)(0) = "pop"
      // Que hacer con myArray(0)(1) = "push"

    }
    else if(sTransition.startsWith("changeTop(")){

    }
    else if(sTransition.startsWith("pushOn(")){

    }
    myArray
  }

  def readTransition(nTransition : String): Boolean ={
    var bool = false
    val sTransition = nTransition.split("")(1)

    if(sTransition.startsWith("pop(")){
      bool = myPop(sTransition)
    }
    else if(sTransition.startsWith("push(")){
      myPush(sTransition)
      bool = true
    }
    else if(sTransition.startsWith("skip(")){
      bool = mySkip(sTransition)
    }
    else if(sTransition.startsWith("changeTop(")){
      bool = myChangeTop(sTransition)
    }
    else if(sTransition.startsWith("pushOn(")){
      bool = myPushOn(sTransition)
    }
    else{
      println("Instruction rejected")
    }

    bool
  }

  def mySkip(sTransition: String): Boolean ={
    var bool = false
    val nStack = sTransition.substring(sTransition.lastIndexOf('(')+1,sTransition.lastIndexOf(')'))
    var rev = ""

    for(n <- nStack){
      rev += myStack.pop
    }

    if(nStack.equals(rev)) {
      myStack.push(rev)
      bool = true
    }

    bool
  }

  def myChangeTop(sTransition: String): Boolean ={
    var bool = false
    val nStack = sTransition.substring(sTransition.lastIndexOf('(')+1,sTransition.lastIndexOf(')'))
    val pop = nStack.split(',')(0)
    val push = nStack.split(',')(1)
    var rev = ""

    for(n <- pop){
      rev += myStack.pop
    }

    if(rev.equals(pop)){
      myStack.push(push)
      bool = true
    }

    bool
  }

  def myPushOn(sTransition: String): Boolean ={
    var bool = false
    val nStack = sTransition.substring(sTransition.lastIndexOf('(')+1,sTransition.lastIndexOf(')'))
    val pop = nStack.split(',')(0)
    val push = nStack.split(',')(1)
    var rev = ""

    for(n <- pop){
      rev += myStack.pop
    }

    if(rev.equals(pop)){
      myStack.push(pop)
      myStack.push(push)
      bool = true
    }

    bool
  }

  def myPop(sTransition: String): Boolean ={
    var bool = false
    val nStack = sTransition.substring(sTransition.lastIndexOf('(')+1,sTransition.lastIndexOf(')'))
    var rev = ""

    for(n <- nStack){
      rev += myStack.pop
    }

    if(!rev.equals(nStack)) {
      myStack.push(rev)
      bool = true
    }

    bool
  }

  def myPush(sTransition: String): Unit ={
    val nStack = sTransition.substring(sTransition.lastIndexOf('(')+1,sTransition.lastIndexOf(')'))
    myStack.push(nStack)
  }
}
