package org.playground.es.uno

import org.playground.es.helpers.EsSpec
import org.playground.es.uno.domain._

class UnoEngineSpec extends EsSpec(UnoEngine.emptyState, UnoEngine.decide, UnoEngine.evolve) {
  // cf https://github.com/thinkbeforecoding/FsUno
  // TODO: property based testing...

  // tests should not rely on state representation as it's an implementation detail and may change often...
  describe("when starting game") {
    it("should start a game") {
      assert(
        given = Seq(),
        when = StartGame(GameId("1"), 4, Digit(Red, 1)),
        expect = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))))
    }
    it("should not start a game with less than two players") {
      assert(
        given = Seq(),
        when = StartGame(GameId("1"), 1, Digit(Red, 1)),
        expect = an[IllegalArgumentException])
    }
    it("should not start a game twice") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))),
        when = StartGame(GameId("1"), 4, Digit(Red, 1)),
        expect = an[IllegalStateException])
    }
  }
  describe("when playing a first card") {
    it("should allow playing a card with same color") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))),
        when = PlayCard(GameId("1"), 0, Digit(Red, 5)),
        expect = Seq(CardPlayed(GameId("1"), 0, Digit(Red, 5))))
    }
    it("should allow playing a card with same value") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))),
        when = PlayCard(GameId("1"), 0, Digit(Blue, 1)),
        expect = Seq(CardPlayed(GameId("1"), 0, Digit(Blue, 1))))
    }
    it("should reject a card with different color and value") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))),
        when = PlayCard(GameId("1"), 0, Digit(Blue, 5)),
        expect = an[IllegalArgumentException])
    }
    it("should reject a card not played by the first player on first turn") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1))),
        when = PlayCard(GameId("1"), 2, Digit(Red, 5)),
        expect = an[IllegalArgumentException])
    }
  }
  describe("when playing a second card") {
    it("should allow playing a card with same color") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1)), CardPlayed(GameId("1"), 0, Digit(Red, 5))),
        when = PlayCard(GameId("1"), 1, Digit(Red, 3)),
        expect = Seq(CardPlayed(GameId("1"), 1, Digit(Red, 3))))
    }
    it("should allow playing a card with same value") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1)), CardPlayed(GameId("1"), 0, Digit(Red, 5))),
        when = PlayCard(GameId("1"), 1, Digit(Blue, 5)),
        expect = Seq(CardPlayed(GameId("1"), 1, Digit(Blue, 5))))
    }
    it("should reject a card with different color and value") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1)), CardPlayed(GameId("1"), 0, Digit(Red, 5))),
        when = PlayCard(GameId("1"), 1, Digit(Blue, 2)),
        expect = an[IllegalArgumentException])
    }
    it("should allow only the second player to play") {
      assert(
        given = Seq(GameStarted(GameId("1"), 4, Digit(Red, 1)), CardPlayed(GameId("1"), 0, Digit(Red, 5))),
        when = PlayCard(GameId("1"), 2, Digit(Blue, 5)),
        expect = an[IllegalArgumentException])
    }
  }
  describe("when playing any tour") {
    it("should go back to the first player when the tour is finished") {
      assert(
        given = Seq(
          GameStarted(GameId("1"), 4, Digit(Red, 1)),
          CardPlayed(GameId("1"), 0, Digit(Red, 5)),
          CardPlayed(GameId("1"), 1, Digit(Blue, 5)),
          CardPlayed(GameId("1"), 2, Digit(Blue, 2)),
          CardPlayed(GameId("1"), 3, Digit(Yellow, 2))
        ),
        when = PlayCard(GameId("1"), 0, Digit(Red, 2)),
        expect = Seq(CardPlayed(GameId("1"), 0, Digit(Red, 2))))
    }
    // take kickback into account...
  }
}
