package org.atnos.eff.addon.cats.mtl.instances

import cats.Applicative
import cats.data.Reader
import cats.mtl._
import org.atnos.eff._

trait EffReaderInstances {
  class EffReaderApplicativeAsk[R, E](implicit m: Reader[E, ?] |= R) extends DefaultApplicativeAsk[Eff[R, ?], E] {
    val applicative: Applicative[Eff[R, ?]] = Eff.EffMonad
    def ask: Eff[R, E] = ReaderEffect.ask
  }

  class EffReaderApplicativeLocal[R, E](implicit m: Reader[E, ?] /= R) extends DefaultApplicativeLocal[Eff[R, ?], E] {
    val ask: ApplicativeAsk[Eff[R, ?], E] = new EffReaderApplicativeAsk
    def local[A](f: E => E)(fa: Eff[R, A]): Eff[R, A] = ReaderEffect.localReader(fa)(f)
  }

  implicit def effReaderApplicativeAsk[R, E](implicit m: Reader[E, ?] |= R): ApplicativeAsk[Eff[R, ?], E] =
    new EffReaderApplicativeAsk

  implicit def effReaderApplicativeLocal[R, E](implicit m: Reader[E, ?] /= R): ApplicativeLocal[Eff[R, ?], E] =
    new EffReaderApplicativeLocal
}

object reader extends EffReaderInstances
