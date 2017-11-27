package org.playground.es.uno

import org.playground.es.common.Event
import org.playground.es.uno.domain.{Card, GameId}

// should be passed
sealed trait UnoEvent extends Event
case class GameStarted(id: GameId, playerCount: Int, firstCard: Card) extends UnoEvent
case class CardPlayed(id: GameId, player: Int, card: Card) extends UnoEvent
