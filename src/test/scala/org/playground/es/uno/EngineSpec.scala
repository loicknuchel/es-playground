package org.playground.es.uno

import org.playground.es.uno.domain.{Digit, GameId, Red}
import org.scalatest.words.ResultOfAnTypeInvocation
import org.scalatest.{FunSpec, Matchers}

class EngineSpec extends FunSpec with Matchers {
  // cf https://github.com/thinkbeforecoding/FsUno
  // TODO: property based testing...

  // tests should not rely on state representation as it's an implementation detail and may change often...
  describe("when starting game") {
    it("should start a game") {
      test(
        given = Seq(),
        when = StartGame(GameId("1"), 4, Digit(Red, 1)),
        expect = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))))
    }
    it("should not start a game with less than two players") {
      test(
        given = Seq(),
        when = StartGame(GameId("1"), 1, Digit(Red, 1)),
        expect = an[IllegalArgumentException])
    }
    it("should not start a game twice") {
      test(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))),
        when = StartGame(GameId("1"), 4, Digit(Red, 1)),
        expect = an[IllegalStateException])
    }
  }
  describe("when playing a first card") {
    it("should allow playing a card with same color") {

    }
    it("should allow playing a card with same value") {

    }
    it("should reject a card with different color and value") {

    }
    it("should reject a card not played by the first player on first turn") {

    }
  }
  describe("when playing a second card") {
    it("should allow playing a card with same color") {

    }
    it("should allow playing a card with same value") {

    }
    it("should reject a card with different color and value") {

    }
    it("should allow only the second player to play") {

    }
  }
  describe("when playing any tour") {
    it("should go back to the first player when the tour is finished") {

    }
    it("should allow only the current player to play") {
      // TODO
      def Given(e: Seq[Event])(when: Command => Unit) = ???
      def When(c: Command) = ???
      Given(Seq())(When(StartGame(GameId("1"), 4, Digit(Red, 1))))
    }
  }

  def test(given: Seq[Event], when: Command, expect: Seq[Event]): Unit = {
    Engine.decide(given.foldLeft(Engine.emptyState)(Engine.evolve), when) shouldBe expect
  }

  def test[T](given: Seq[Event], when: Command, expect: ResultOfAnTypeInvocation[T]): Unit = {
    expect should be thrownBy Engine.decide(given.foldLeft(Engine.emptyState)(Engine.evolve), when)
  }
}
