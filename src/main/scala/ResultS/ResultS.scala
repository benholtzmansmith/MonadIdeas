package ResultS


/**
 * Created by benjaminsmith on 1/28/16.
 *
 * Modeled as an inverse Option.
 * ResultM is designed for sequencing operations that fast-fail on the
 * first success and continue on all failures.
 *
 */

trait ResultM[+A]{
  def isFailure:Boolean
  def isSuccess:Boolean
  def get:A
  def map[B](f: A => B):ResultM[B] = {
    if (isFailure) {
      Failure(f(this.get))
    }
    else Success
  }
  def flatMap[B](f: A => ResultM[B]):ResultM[B] = {
    if (isFailure){
      f(this.get)
    }
    else {
      Success
    }
  }
  def filter(f: A => Boolean):ResultM[A] =
    if (isSuccess || f(this.get)) this else Success
}

case class Failure[+A](a:A) extends ResultM[A] {
  def isFailure: Boolean = true
  def isSuccess:Boolean = false
  def get: A = a
}

case object Success extends ResultM[Nothing] {
  def isFailure: Boolean = false
  def isSuccess:Boolean = true
  def get: Nothing = throw new Exception("Noting returned in success")
}

object ResultM{
  def apply[A](a:A):ResultM[A] = Failure(a)
}


