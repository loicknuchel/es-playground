package org.playground.es.uno

import org.playground.es.common.State
import org.playground.es.uno.domain.{Card, Digit, KickBack}

object UnoEngine {

  case class UnoState(playerCount: Int, topCard: Option[Card], turn: Int) extends State {
    def started: Boolean = topCard.isDefined

    def canPlay(card: Card): Boolean = (topCard, card) match {
      case (Some(Digit(c1, v1)), Digit(c2, v2)) => c1 == c2 || v1 == v2
      case (Some(Digit(c1, _)), KickBack(c2)) => c1 == c2
      case (Some(KickBack(c1)), Digit(c2, _)) => c1 == c2
      case (Some(KickBack(c1)), KickBack(c2)) => c1 == c2
      case (None, _) => false
    }
  }

  val emptyState = UnoState(playerCount = 0, topCard = None, turn = -1)

  // should hold all business logic
  def decide(s: UnoState, c: UnoCommand): Seq[UnoEvent] = c match {
    case StartGame(_, playerCount, _) if playerCount < 2 => throw new IllegalArgumentException("Can't start a game with less than 2 players")
    case StartGame(_, _, _) if s.started => throw new IllegalStateException("Can't start a game twice")
    case StartGame(id, playerCount, firstCard) if playerCount > 1 => Seq(GameStarted(id, playerCount, firstCard))
    case PlayCard(_, player, _) if s.turn != player => throw new IllegalArgumentException(s"Player $player can't play on ${s.turn} turn")
    case PlayCard(_, _, card) if !s.canPlay(card) => throw new IllegalArgumentException(s"Can't play $card on a ${s.topCard}")
    case PlayCard(id, player, card) => Seq(CardPlayed(id, player, card))
  }

  // should stay as dumb as possible
  def evolve(s: UnoState, e: UnoEvent): UnoState = e match {
    case GameStarted(_, playerCount, firstCard) => s.copy(playerCount = playerCount, topCard = Some(firstCard), turn = 0)
    case CardPlayed(_, _, card) => s.copy(topCard = Some(card), turn = (s.turn + 1) % s.playerCount)
    case _ => s
  }
}
