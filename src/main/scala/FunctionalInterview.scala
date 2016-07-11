
object Questions {

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
  /**
    * Using somInt apply some methods that take functions with signature of iToS
    * and sToL to return a value that works with the requirements for somL.
    * */

  val somL:Opt[List[String]] = ???

  ////(2)
  val somString:Opt[String] = ???
  val sToOptInt:String => Opt[Int] = ???
  /**
    * Using someString apply a method that takes a function with signature of sToOptInt
    * to return a value that works with the requirements for optInt.
    * */
  val optInt:Opt[Int] = ???


  ////(3)
  val somString2:Opt[String] = ???
  val sToOptInt2:String => Opt[Int] = ???
  val intToS:Int => (String, String) = ???
  /**
    * Using someString2 apply some methods that take a function with signature of sToOptInt2
    * and intToS to return a value that works with the requirements for optInt2.
    * */
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

  /**
    * Starting with futOptInt,
    * find away to apply the function intToSTring to futOptInt to
    * solve the unimplemented value of futOptString below.
    * */

  val futOptString:Fut[Opt[String]] = ???

  /**
    * Can your solution be used inside of a flatMap to
    * complete the example below? If not implement a solution that
    * can be used within a flatMap.
    * */

  for {
    f:Int <- futOptInt
  } yield intToSTring(f)

}