import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scalaz._
import Scalaz._

object Problem {

 /**
  *
  * How can you enable using the for comprehension inside of AsyncCall with nested
  * monadic types.
  *
  * First create a solution for this problem that presumes that the nested Monadic types
  * will be Future[Option[A]].
  *
  * Second create a solution that is neutral to the outer Monadic type -> F[Option[A]].
  *
  * def asyncCall(id:String):Future[Option[Int]] = ???
  *
  * def anotherAsyncCall(t:Int):Future[Option[String]] = ???
  *
  * def composedAsync(id:String):Future[Option[String]] =
  *   for {
  *     result1:Int <- asyncCall(id)
  *     if result1 > 2
  *     result2 <- anotherAsyncCall(result1)
  *   } yield result2
*/
}

object OptionTransformer {
  import scala.concurrent.ExecutionContext.Implicits.global


  def asyncCall(id:String):Future[Option[Int]] = ???

  def anotherAsyncCall(t:Int):Future[Option[String]] = ???

  def composedAsync(id:String):Future[Option[String]] = {
    val transformedResult = for {
      result1 <- FutureOption[Int](asyncCall(id))
      if result1 > 2
      result2 <- FutureOption[String](anotherAsyncCall(result1))
    } yield result2
    transformedResult.run
  }

  implicit val futureMonad = FutureMonad.apply().futureMonad

  def composedAsyncGeneric(id:String):Future[Option[String]] = {
    val transformedResult = for {
      result1 <- GenericOption[Future, Int](asyncCall(id))
      if result1 > 2
      result2 <- GenericOption[Future, String](anotherAsyncCall(result1))
    } yield result2
    transformedResult.run
  }
}

case class FutureOption[+A](h:Future[Option[A]]) {
  def map[B](a: (A) => B)(implicit exec:ExecutionContext): FutureOption[B] = FutureOption( h.map( _.map( a ) ) )

  def flatMap[B](a: (A) => FutureOption[B])(implicit exec:ExecutionContext): FutureOption[B] = {
    val future = h.flatMap{
      case Some(x) => a(x).h
      case None => Future.successful(None)
    }
    FutureOption(future)
  }

  def filter(a: A => Boolean)(implicit exec:ExecutionContext):FutureOption[A] = {
    val filteredResult = h.map( f => {
      if(f.exists(a)) f
      else None
    })
    FutureOption(filteredResult)
  }

  def run = h
}

/** Generic answer. Used with any Monad wrapper of Option */
case class GenericOption[F[_], A](a:F[Option[A]]){

  def map[B](f:A => B)(implicit F: Functor[F]):GenericOption[F, B] = 
    GenericOption( a.map(_.map( f ) ) )

  def flatMap[B](f:A => GenericOption[F, B])(implicit F: Monad[F]): GenericOption[F, B] = 
    GenericOption( a.flatMap{
      case Some( x ) => f( x ).a
      case None => F.pure( None )
      } )

  def filter(f:A => Boolean)(implicit F: Monad[F]):GenericOption[F, A] = GenericOption(a.map( _.find(f)))

  def run = a
}

case class FutureMonad(implicit exec:ExecutionContext) {
  def futureMonad:Monad[Future] = new Monad[Future] {
    def pure[A](a: => A): Future[A] = Future { a }

    def bind[A, B](a: Future[A], f: (A) => Future[B]): Future[B] = a.flatMap(f)
  }
}


