package org.atnos.eff.addon.cats.mtl.instances

import cats._
import cats.data.State
import cats.mtl._
import org.atnos.eff._

trait EffStateInstances {
  class EffStateApplicativeAsk[R, S](implicit m: State[S, ?] |= R) extends DefaultApplicativeAsk[Eff[R, ?], S] {
    val applicative: Applicative[Eff[R, ?]] = Eff.EffMonad
    def ask: Eff[R, S] = StateEffect.get
  }

  class EffStateApplicativeLocal[R, S](implicit m: State[S, ?] /= R) extends DefaultApplicativeLocal[Eff[R, ?], S] {
    val ask = new EffStateApplicativeAsk
    def local[A](f: S => S)(fa: Eff[R, A]): Eff[R, A] = StateEffect.localState(fa)(f)
  }

  class EffStateFunctorTell[R, S](implicit m: State[S, ?] |= R) extends DefaultFunctorTell[Eff[R, ?], S] {
    val functor: Functor[Eff[R, ?]] = Eff.EffMonad
    def tell(s: S): Eff[R, Unit] = StateEffect.put(s)
  }

  class EffStateFunctorListen[R, S](implicit m: State[S, ?] |= R) extends FunctorListen[Eff[R, ?], S] {
    val tell: FunctorTell[Eff[R, ?], S] = new EffStateFunctorTell
    def listen[A](fa: Eff[R, A]): Eff[R, (A, S)] = Eff.EffMonad.product(fa, StateEffect.get)
    def listens[A, B](fa: Eff[R, A])(f: S => B): Eff[R, (A, B)] = Eff.EffMonad.product(fa, StateEffect.get.map(f))
  }

  class EffStateMonadState[R, S](implicit m: State[S, ?] |= R) extends DefaultMonadState[Eff[R, ?], S] {
    val monad: Monad[Eff[R, ?]] = Eff.EffMonad
    def get: Eff[R, S] = StateEffect.get
    def set(s: S): Eff[R, Unit] = StateEffect.put(s)
  }

  implicit def effStateApplicativeAsk[R, S](implicit m: State[S, ?] |= R): ApplicativeAsk[Eff[R, ?], S] =
    new EffStateApplicativeAsk

  implicit def effStateApplicativeListen[R, S](implicit m: State[S, ?] /= R): ApplicativeLocal[Eff[R, ?], S] =
    new EffStateApplicativeLocal

  implicit def effStateFunctorTell[R, S](implicit m: State[S, ?] |= R): FunctorTell[Eff[R, ?], S] =
    new EffStateFunctorTell

  implicit def effStateFunctorListen[R, S](implicit m: State[S, ?] |= R): FunctorListen[Eff[R, ?], S] =
    new EffStateFunctorListen

  implicit def effStateMonadState[R, S](implicit m: State[S, ?] |= R): MonadState[Eff[R, ?], S] =
    new EffStateMonadState
}

object state extends EffStateInstances
