/** Documentation URL: https://kotlinlang.org/docs/delegated-properties.html#standard-delegates **/ 

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
import kotlin.reflect.KProperty
import kotlin.properties.Delegates

fun main() {
  val e = Example()
  e.p = "New"
  println(e.p)
  
  /** 
   * Lazy properties: 
   *  
   * lazy() is a function that takes a lambda and returns an instance of Lazy<T>, 
   * which can serve as a delegate for implementing a lazy property. 
   * The first call to get() executes the lambda passed to lazy() and remembers the result. 
   * Subsequent calls to get() simply return the remembered result.
   * 
   * By default, the evaluation of lazy properties is synchronized: the value is computed only in one thread, 
   * but all threads will see the same value.
   * 
   ***/ 
  val lazyValue: String by lazy{
      println("I am first time calling or initializing")
      "I am a remember value!"
  }
  
  println(lazyValue)
  println(lazyValue)
 
  // example of Observable properties. 	
  val user = User()
  user.name = "First"
  user.name = "Second"
    
  // example of another properties delegation. 
  val myClass = MyClass()
  myClass.oldName = 10 
  println(myClass.newName)  
}


 /**
  * Observable properties: 
  * 
  * Delegates.observable() takes two arguments: the initial value and a handler for modifications.
  * The handler is called every time you assign to the property (after the assignment has been performed). 
  * It has three parameters: the property being assigned to, the old value, and the new value:
  * @check User Class 
  ***/

class User{
  var name: String by Delegates.observable("no_name"){
      prop, old, new ->
      println("$old -> $new")
  }
}

/**
 * Delegating to another property
 * A property can delegate its getter and setter to another property. 
 * Such delegation is available for both top-level and class properties (member and extension). 
 * The delegate property can be:
 * A top-level property
 * A member or an extension property of the same class
 * A member or an extension property of another class
 * To delegate a property to another property, use the :: qualifier in the delegate name, 
 * for example, this::delegate or MyClass::delegate.
 * 
 * **/
class MyClass{
    var newName: Int = 0 
    var oldName: Int by this::newName
}

class Example{
  var p : String by Delegate() 
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
