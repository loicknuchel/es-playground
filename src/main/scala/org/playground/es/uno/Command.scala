package org.playground.es.uno

import org.playground.es.uno.domain.{Card, GameId}

// should be orders
sealed trait Command
case class StartGame(id: GameId, playerCount: Int, firstCard: Card) extends Command
