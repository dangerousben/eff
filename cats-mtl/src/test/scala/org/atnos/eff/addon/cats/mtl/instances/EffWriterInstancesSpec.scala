package org.atnos.eff.addon.cats.mtl.instances

import cats.mtl.laws.discipline.FunctorTellTests
import writer._

class EffWriterInstancesSpec extends BaseSpec { def is = s2"""
 $functorTellLaws
"""

  def functorTellLaws = checkAll(Program, FunctorTellTests[Program, String].functorTell[String])
}
