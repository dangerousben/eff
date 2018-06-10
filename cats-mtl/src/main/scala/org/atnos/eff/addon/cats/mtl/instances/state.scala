package org.atnos.eff.addon.cats.mtl.instances

import cats.Monad
import cats.data.State
import cats.mtl._
import org.atnos.eff._

trait EffStateInstances {
  class EffStateMonadState[R, S](implicit m: State[S, ?] |= R) extends DefaultMonadState[Eff[R, ?], S] {
    val monad: Monad[Eff[R, ?]] = Eff.EffMonad
    def get: Eff[R, S] = StateEffect.get
    def set(s: S): Eff[R, Unit] = StateEffect.put(s)
  }

  implicit def effStateMonadState[R, S](implicit m: State[S, ?] |= R): MonadState[Eff[R, ?], S] =
    new EffStateMonadState
}

object state extends EffStateInstances
