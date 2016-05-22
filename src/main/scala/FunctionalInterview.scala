
object OptionMonad {

  //Fill in ???
  ////////////////////////////////////////////////////////////
  def partial[A,B,C](a:A, f:(A,B) => C):B => C = {
    (b:B) => ???
  }

  def uncurry[A,B,C](f:(A) => (B => C)):(A, B) => C = {
    case (a:A, b:B) => ???
  }
  ////////////////////////////////////////////////////////////

  trait Opt[A]{
    def map[B](f:A => B):Opt[B]
    def flatMap[B](f:A => Opt[B]):Opt[B]
  }

  case class Som[A](a:A) extends Opt[A] {
    def map[B](f: (A) => B): Opt[B] = ???
    def flatMap[B](f: (A) => Opt[B]): Opt[B] = ???
  }

  ////(1)
  val somInt:Som[Int] = ???
  val iToS:Int => String = { i => i.toString }
  val sToL:String => List[String] = {s => List(s) }
  //Using somInt go to somL
  val somL:Opt[List[String]] = ???

  ////(2)
  val somString:Opt[String] = ???
  val sToOptInt:String => Opt[Int] = ???
  val optInt:Opt[Int] = ???


  ////(3)
  val somString2:Opt[String] = ???
  val sToOptInt2:String => Opt[Int] = ???
  val intToS:Int => (String, String) = ???
  val optInt2:Opt[(String, String)] = ???

  ////(4)
  case class Non[A]() extends Opt[A] {
    def map[B](f: (A) => B): Opt[B] = Non[B]()
    def flatMap[B](f: (A) => Opt[B]): Opt[B] = Non[B]()
  }

  val opt:Opt[Int] = Som(1)

  opt match {
    case Som( a ) => a.toString
    case Non() => ""
  }

  val optOptInt:Opt[Opt[Int]] = Som(Som(1))

  opt match {
    case ??? => ???
    case ??? => ???
  }


  ////////////////////////////////////////////////////////////


  trait Fut[A]{
    def map[B](f:A => B):Fut[B]
    def flatMap[B](f:A => Fut[B]):Fut[B]
  }

  case class Suc[A](a:A) extends Fut[A] {
    def map[B](f: (A) => B): Fut[B] = ???
    def flatMap[B](f: (A) => Fut[B]): Fut[B] = ???
  }

  case class Fail[A]() extends Fut[A] {
    def map[B](f: (A) => B): Fut[B] = Fail[B]()
    def flatMap[B](f: (A) => Fut[B]): Fut[B] = Fail[B]()
  }

  val futOptInt:Fut[Opt[Int]] = ???
  val intToSTring:Int => Fut[Opt[String]] = ???

  //Starting from futOptInt and using intToString, resolve ???

  val futOptString:Fut[Opt[String]] = ???

}