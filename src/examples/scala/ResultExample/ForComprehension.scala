package ResultExample

import ResultS._

/**
 * Created by benjaminsmith on 1/30/16.
 */
object ForComprehension {
  def mockExternalRequest(x:Int):Boolean = {
    if (x > 20) true
    else false
  }
  def mockExternalPost(x:Int):Unit = {
    ()
  }

  def strategy1(x:Int):ResultS[Unit] = {
    if (mockExternalRequest(x + 1)) {
      mockExternalPost(x)
      Success
    }
    else Failure(())
  }

  def strategy2(x:Int):ResultS[Unit] = {
    if (mockExternalRequest(x + 2)) {
      mockExternalPost(x)
      Success
    }
    else Failure(())
  }

  def strategy3(x:Int):ResultS[Unit] = {
    if (mockExternalRequest(x + 3)) {
      mockExternalPost(x)
      Success
    }
    else Failure(())
  }

  def demonstrate(x:Int):Unit = {
    val result = for {
      _ <- strategy1(x)
      _ <- strategy2(x)
      finalR <- strategy3(x)
    } yield {
      finalR
    }

    result match {
      case Failure(_) => println(s"No strategy worked for checking $x")
      case Success => println("A strategy was successful in checking $x")
    }
  }
}
