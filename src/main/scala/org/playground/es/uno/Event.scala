package org.playground.es.uno

import org.playground.es.uno.domain.{Card, GameId}

// should be passed
sealed trait Event
case class GameStarted(id: GameId, playerCount: Int, firstCard: Card) extends Event
