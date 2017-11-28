package org.playground.es.uno

import org.playground.es.common.State
import org.playground.es.uno.domain.Card

object UnoEngine {

  case class UnoState(topCard: Option[Card], playerCount: Int, turn: Int) extends State {
    def isStarted: Boolean = topCard.isDefined

    def canPlay(card: Card): Boolean = topCard.exists(_.isSimilar(card))

    def nextTurn: Int = (turn + 1) % playerCount
  }

  val emptyState = UnoState(topCard = None, playerCount = 0, turn = -1)

  // should hold all business logic
  def decide(s: UnoState, c: UnoCommand): Seq[UnoEvent] = c match {
    case StartGame(_, playerCount, _) if playerCount < 2 => throw new IllegalArgumentException("Can't start a game with less than 2 players")
    case StartGame(_, _, _) if s.isStarted => throw new IllegalStateException("Can't start a game twice")
    case StartGame(id, playerCount, firstCard) => Seq(GameStarted(id, playerCount, firstCard))
    case PlayCard(_, player, _) if s.turn != player => throw new IllegalArgumentException(s"Player $player can't play on ${s.turn} turn")
    case PlayCard(_, _, card) if !s.canPlay(card) => throw new IllegalArgumentException(s"Can't play $card on a ${s.topCard}")
    case PlayCard(id, player, card) => Seq(CardPlayed(id, player, card))
  }

  // should stay as dumb as possible
  def evolve(s: UnoState, e: UnoEvent): UnoState = e match {
    case GameStarted(_, playerCount, firstCard) => s.copy(playerCount = playerCount, topCard = Some(firstCard), turn = 0)
    case CardPlayed(_, _, card) => s.copy(topCard = Some(card), turn = s.nextTurn)
    case _ => s
  }

  def evolveAll(s: UnoState, e: Seq[UnoEvent]): UnoState =
    e.foldLeft(s)(evolve)

  def decideAndEvolve(s: UnoState, c: UnoCommand): UnoState =
    evolveAll(s, decide(s, c))

  def decideAndEvolveAll(s: UnoState, c: Seq[UnoCommand]): UnoState =
    c.foldLeft(s)(decideAndEvolve)
}
