package org.playground.es.helpers

import org.scalatest.words.ResultOfAnTypeInvocation
import org.scalatest.{FunSpec, Matchers}

class EsSpec[S, C, E](initialState: S, decide: (S, C) => Seq[E], evolve: (S, E) => S) extends FunSpec with Matchers {
  def assert(given: Seq[E], when: C, expect: Seq[E]): Unit =
    decide(given.foldLeft(initialState)(evolve), when) shouldBe expect

  def assert[T](given: Seq[E], when: C, expect: ResultOfAnTypeInvocation[T]): Unit =
    expect should be thrownBy decide(given.foldLeft(initialState)(evolve), when)
}
