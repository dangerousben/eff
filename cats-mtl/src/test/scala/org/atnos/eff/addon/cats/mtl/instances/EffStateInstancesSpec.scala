package org.atnos.eff.addon.cats.mtl.instances

import cats.mtl.laws.discipline.MonadStateTests
import state._

class EffStateInstancesSpec extends BaseSpec { def is = s2"""
 $monadStateLaws
"""

  def monadStateLaws = checkAll(Program, MonadStateTests[Program, String].monadState[String])
}
