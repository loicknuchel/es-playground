package org.playground.es.uno.domain

import java.util.UUID

case class GameId(value: String)

object GameId {
  def generate: GameId = GameId(UUID.randomUUID().toString)
}
