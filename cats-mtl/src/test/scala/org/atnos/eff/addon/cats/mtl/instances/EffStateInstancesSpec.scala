package org.atnos.eff.addon.cats.mtl.instances

import cats.mtl.laws.discipline._
import state._

class EffStateInstancesSpec extends BaseSpec { def is = s2"""
 $applicativeLocalLaws
 $functorListenLaws
 $monadStateLaws
"""

  def applicativeLocalLaws = checkAll(Program, ApplicativeLocalTests[Program, String].applicativeLocal[String, String])
  def functorListenLaws = checkAll(Program, FunctorListenTests[Program, String].functorListen[String, String])
  def monadStateLaws = checkAll(Program, MonadStateTests[Program, String].monadState[String])
}
