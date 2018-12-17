package example

trait User {
  val child: Option[User]
}

object UserService {
  def loadUser(name: String): Option[User] =
    None
}

sealed trait Maybe[+A] {
  def flatMap[B](f: A => Maybe[B]): Maybe[B] = this match {
    case Just(a) => f(a)
    case MaybeNot => MaybeNot
  }
}

case class Just[+A](a: A) extends Maybe[A]
case object MaybeNot extends Maybe[Nothing]

object Main extends App {

  val getChild = (user: User) => user.child

  val result = for {
    user            <- UserService.loadUser("mike")
    usersChild      <- user.child
    usersGrandChild <- usersChild.child
  } yield usersGrandChild

  println(result)
}
