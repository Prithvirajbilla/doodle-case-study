package doodle.core
import scala.util.Random

/**
  * Created by prithvirajbilla on 5/9/17.
  */
sealed trait Random[A] {
  def run(rng: scala.util.Random):A = this match {
    case Primitive
  }

  def flatMap[B](f: A => Random[B]):Random[B] =

  def map[B](f: A => B): Random[B] = ???

  def zip[B](that: Random[B]): Random[(A,B)] = this flatMap {
    a => that map {
      b => (a,b)
    }
  }

}


object Random {
  val double: Random[Double] = new Random[Double] {
    override def run(rng: scala.util.Random): Double = rng.nextDouble()
  }

  val int: Random[Int] = new Random[Int] {
    override def run(rng: scala.util.Random): Double = rng.nextDouble()
  }

  val normal: Random[Double] = new Random[Double] {
    override def run(rng: scala.util.Random): Double = rng.nextGaussian()
  }

  def always[A](in: A): Random[A] = new Random[A] {
    override def run(rng: scala.util.Random): A =3
  }

  final case class Primitive[A](f: scala.util.Random => A) extends Random[A]
  final case class FlatMap[A,B](random: Random[A], f: A => Random[B]) extends Random[B]
  final case class Map[A,B](random: Random[A], f: A=> B) extends Random[B]
}
