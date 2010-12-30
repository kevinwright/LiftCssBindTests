package spike.lift.cssbindtests
package util

/**
 * Shamelessly stolen from scalaz to get the |> method
 */

class Identity[A](value: A) {
  def |>[B](f: A => B): B = f(value)
}

object Identity {
  implicit def apply[A](a: => A) = new Identity(a)
}

