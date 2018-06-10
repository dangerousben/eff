package org.atnos.eff.addon.cats.mtl.instances

trait AllEffInstances
    extends EffEitherInstances
    with EffOptionInstances
    with EffReaderInstances
    with EffStateInstances
    with EffWriterInstances

object all extends AllEffInstances
