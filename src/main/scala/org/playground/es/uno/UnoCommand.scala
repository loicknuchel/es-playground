package org.playground.es.uno

import org.playground.es.common.Command
import org.playground.es.uno.domain.{Card, GameId}

// should be orders
sealed trait UnoCommand extends Command
case class StartGame(id: GameId, playerCount: Int, firstCard: Card) extends UnoCommand
case class PlayCard(id: GameId, player: Int, card: Card) extends UnoCommand
