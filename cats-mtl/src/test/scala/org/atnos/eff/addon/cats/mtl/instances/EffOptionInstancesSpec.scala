package org.atnos.eff.addon.cats.mtl.instances

import cats.mtl.laws.discipline.FunctorEmptyTests
import option._

class EffOptionInstancesSpec extends BaseSpec { def is = s2"""
 $functorEmptyLaws
"""

  def functorEmptyLaws = checkAll(Program, FunctorEmptyTests[Program].functorEmpty[String, String, String])
}
