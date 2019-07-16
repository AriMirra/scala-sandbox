package tp1

case class Person(name: String, age: Int, children: List[Person] = Nil) {

  override def toString: String = name

  def descendants: List[Person] = this :: children.flatMap(_.descendants)

  def ageSortChildren: (List[Person], List[Person]) = descendants.partition(_.age > 18)

  def virginChildren: List[Person] = descendants.filter(_.children.isEmpty)

  def parentChildren: List[Person] = descendants.filter(_.children.nonEmpty)

  val childrenByAge: List[Person] = children.sortBy(_.age)

  def consecutiveChildren: List[(Person, Person)] = childrenByAge zip childrenByAge.tail

  def twinChildren: List[(Person, Person)] = consecutiveChildren filter {
    case (p1, p2) => p1.age == p2.age
  }

  def twins: List[(Person, Person)] = parentChildren.flatMap(_.twinChildren)

  def oldSiblings: List[(Person, Person)] = consecutiveChildren filter {
    case (p1, p2) => p1.age - p2.age > 4
  }

  def grandChildren: List[Person] = children.flatMap(_.children)

  def averageChildrenAge: Int =
    children.foldLeft(0)(_ + _.age) / children.length

  def withChildrenAvgAge(average: Int): List[Person] =
    parentChildren.filter(_.averageChildrenAge == average)

  def withParentsOlderThan(age: Int): List[Person] =
    parentChildren.filter(_.age > age).flatMap(_.children)

}

object PersonApp extends App {
  val ariel = Person("ariel", 21, Nil)
  val fabiana = Person("fabiana", 21, Nil)
  val pelado = Person("francisquito", 2, Nil)
  val marcelo = Person("marcelo", 33, List(pelado))

  val sergio = Person("sergio", 50, List(ariel,fabiana, marcelo))
  val miriam = Person("miriam", 55, List(ariel,fabiana, marcelo))

  println(sergio.grandChildren)
}
