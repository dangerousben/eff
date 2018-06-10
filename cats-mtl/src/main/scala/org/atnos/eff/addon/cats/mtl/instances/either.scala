package org.atnos.eff.addon.cats.mtl.instances

import cats.Functor
import cats.mtl._
import org.atnos.eff._

trait EffEitherInstances {
  class EffEitherFunctorRaise[R, E](implicit m: Either[E, ?] |= R) extends FunctorRaise[Eff[R, ?], E] {
    val functor: Functor[Eff[R, ?]] = Eff.EffMonad
    def raise[A](e: E): Eff[R, A] = EitherEffect.left(e)
  }

  implicit def effEitherFunctorRaise[R, E](implicit m: Either[E, ?] |= R): FunctorRaise[Eff[R, ?], E] =
    new EffEitherFunctorRaise
}

object either extends EffEitherInstances
