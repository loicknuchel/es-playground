package org.playground.es.uno

import org.scalacheck.Prop.{BooleanOperators, forAll}
import org.scalacheck.Properties
import org.scalacheck.ScalacheckShapeless._

import scala.util.Try

object UnoEngineProperties extends Properties("UnoEngine") {
  property("start game") = forAll { (c: StartGame) =>
    (c.playerCount >= 2) ==> (UnoEngine.decideAndEvolve(UnoEngine.emptyState, c).isStarted == true)
  }
  property("not start game twice") = forAll { (c: StartGame) =>
    (c.playerCount >= 2) ==> (Try(UnoEngine.decideAndEvolveAll(UnoEngine.emptyState, Seq(c, c))).isFailure == true)
  }
}
