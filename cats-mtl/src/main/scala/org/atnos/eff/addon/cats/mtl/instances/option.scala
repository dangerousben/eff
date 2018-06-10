package org.atnos.eff.addon.cats.mtl.instances

import cats.Functor
import cats.mtl._
import org.atnos.eff._

trait EffOptionInstances {
  class EffOptionFunctorEmpty[R](implicit m: Option |= R) extends DefaultFunctorEmpty[Eff[R, ?]] {
    val functor: Functor[Eff[R, ?]] = Eff.EffMonad
    def mapFilter[A, B](fa: Eff[R, A])(f: A => Option[B]): Eff[R, B] = fa.flatMap(a => OptionEffect.fromOption(f(a)))
  }

  class EffOptionFunctorRaise[R](implicit m: Option |= R) extends FunctorRaise[Eff[R, ?], Unit] {
    val functor: Functor[Eff[R, ?]] = Eff.EffMonad
    def raise[A](e: Unit): Eff[R, A] = OptionEffect.none
  }

  implicit def effOptionFunctorEmpty[R](implicit m: Option |= R): FunctorEmpty[Eff[R, ?]] =
    new EffOptionFunctorEmpty

  implicit def effOptionFunctorRaise[R](implicit m: Option |= R): FunctorRaise[Eff[R, ?], Unit] =
    new EffOptionFunctorRaise
}

object option extends EffOptionInstances
