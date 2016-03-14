import scala.concurrent.Future


object Problem {

 /**
  *
  * How can you enable using the for comprehension inside of AsyncCall with nested
  * monadic types.
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

  case class FutureOption[+A](h:Future[Option[A]]) {
    import scala.concurrent.ExecutionContext.Implicits.global
    def flatMap[B](a: (A) => FutureOption[B]): FutureOption[B] = {
      val future = h.flatMap{
        case Some(x) => a(x).h
        case None => Future.successful(None)
      }
      FutureOption(future)
    }

    def map[B](a: (A) => B): FutureOption[B] = FutureOption(h.map(_.map(a)))

    def filter(a: A => Boolean):FutureOption[A] = {
      val filteredResult = h.map( f => {
        if(f.exists(a)) f
        else None
      })
      FutureOption(filteredResult)
    }

    def run = h
  }
}



