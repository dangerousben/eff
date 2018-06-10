package org.atnos.eff.addon.cats.mtl.instances

import cats.Eq
import cats.data._
import cats.instances.AllInstances
import org.atnos.eff._
import org.atnos.eff.syntax.all._
import org.scalacheck._
import org.scalacheck.Arbitrary.arbitrary
import org.specs2.Specification
import org.typelevel.discipline.specs2.Discipline

abstract class BaseSpec extends Specification with Discipline with AllInstances {
  type Stack = Fx.fx4[Reader[String, ?], Writer[String, ?], State[String, ?], Option]
  type Program[A] = Eff[Stack, A]
  val Program = "Eff[Fx.fx4[Reader[String, ?], Writer[String, ?], State[String, ?], Option], String]"

  implicit def arbProgram[A](implicit a: Arbitrary[A], aa: Arbitrary[A => A]): Arbitrary[Program[A]] =
    Arbitrary(
      Gen.tailRecM[A => Program[A], Program[A]](Eff.pure)(f =>
        Gen.oneOf(
          genKleisli[A, A, Stack].map(g => Left((a: A) => f(a).flatMap(g))),
          arbitrary[A].map(a => Right(f(a))),
        )
      )
    )

  def genKleisli[A, B, R]()(implicit
    aab: Arbitrary[A => B],
    asb: Arbitrary[String => B],
    mr: Reader[String, ?] |= R,
    mw: Writer[String, ?] |= R,
    ms: State[String, ?] |= R,
    mo: Option |= R,
  ): Gen[A => Eff[R, B]] =
    Gen.oneOf(
      aab.arbitrary.map(_ andThen Eff.pure[R, B]),
      asb.arbitrary.map(f => (_: A) => ReaderEffect.ask.map(f)),
      for (f <- aab.arbitrary; s <- arbitrary[String]) yield (a: A) => WriterEffect.tell(s).map(_ => f(a)),
      asb.arbitrary.map(f => (_: A) => StateEffect.gets(f)),
      (for (f <- aab.arbitrary; s <- arbitrary[String]) yield (a: A) => StateEffect.put(s).map(_ => f(a))),
      for (f <- aab.arbitrary; g <- arbitrary[String => String]) yield (a: A) => StateEffect.modify(g).map(_ => f(a)),
      Gen.const((_: A) => OptionEffect.none[R, B]),
      aab.arbitrary.map(_ andThen OptionEffect.some[R, B]),
    )

  implicit def eqProgram[A : Eq]: Eq[Program[A]] = new Eq[Program[A]] {
    def eqv(x: Program[A], y: Program[A]): Boolean = Eq.eqv(runProgram(x), runProgram(y))
  }

  def runProgram[A](p: Program[A]) = p.runReader("foo").runWriter.runState("bar").runOption.run
}
