package demo

class A {
  def value: String = "A"
}

trait T1 {
  def helloT1 = "I'm T1"
  def hello = "I'm T1"
}

trait T2 {
  def helloT2 = "I'm T2"
  def hello = "I'm T2"
}

class B extends T1 with T2{
  override def hello: String = super[T2].hello
}

object Mixins extends App {
  println(new B().helloT1)
  println(new B().helloT2)
  println(new B().hello)
}

class Person(val name: String)

trait MutableChildren {
  private var children: List[Person] = Nil

  def addChild(child: Person): Unit = children = child :: children
  def getChildren():List[Person] = children
}

trait Friendly {
  def hello(name: String): String = s"hello $name"
}

object Mixins2 extends App {

  type FriendlyParent = Person with MutableChildren with Friendly

  val person: FriendlyParent = new Person("Juan") with MutableChildren with Friendly
  person.addChild(new Person("X"))

  println(person.hello("Z"))
}
