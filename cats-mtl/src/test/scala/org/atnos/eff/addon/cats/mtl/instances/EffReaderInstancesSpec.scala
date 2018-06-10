package org.atnos.eff.addon.cats.mtl.instances

import cats.mtl.laws.discipline.ApplicativeLocalTests
import reader._

class EffReaderInstancesSpec extends BaseSpec { def is = s2"""
 $applicativeLocalLaws
"""

  def applicativeLocalLaws = checkAll(Program, ApplicativeLocalTests[Program, String].applicativeLocal[String, String])
}
