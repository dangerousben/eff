package org.atnos.eff.addon.cats.mtl.instances

import cats.Functor
import cats.data.Writer
import cats.mtl._
import org.atnos.eff._

trait EffWriterInstances {
  class EffWriterFunctorTell[R, L](implicit m: Writer[L, ?] |= R) extends DefaultFunctorTell[Eff[R, ?], L] {
    val functor: Functor[Eff[R, ?]] = Eff.EffMonad[R]
    def tell(l: L): Eff[R, Unit] = WriterEffect.tell(l)
  }

  implicit def effWriterFunctorTell[R, L](implicit m: Writer[L, ?] |= R): FunctorTell[Eff[R, ?], L] =
    new EffWriterFunctorTell
}

object writer extends EffWriterInstances
