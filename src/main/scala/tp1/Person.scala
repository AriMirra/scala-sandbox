package tp1

case class Person(name: String, age: Int, children: List[Person] = Nil) {

  def descendants: List[Person] = this :: children.flatMap(_.descendants)

  def ageSortChildren: (List[Person], List[Person]) = children.partition(_.age < 18)

  def virginChildren: List[Person] = children.filter(_.children == Nil)

  def twinChildren: List[(Person, Person)] = {
    val combinations = for (x <- children; y <- children) yield (x, y)
    combinations.filter { case (a, b) => a.age == b.age && a.name != b.name }
    //    children.combinations(2)
    //      .filter(list => {list.head.age == list.last.age && list.head.name != list.last.name})
    //      .toArray
  }
}

object PersonApp extends App {
  val ariel = Person("ariel", 20, Nil)
  val fabiana = Person("fabiana", 20, Nil)

  val sergio = Person("sergio", 50, List(ariel,fabiana))
  val miriam = Person("miriam", 55, List(ariel,fabiana))

  println(sergio.descendants)
}
