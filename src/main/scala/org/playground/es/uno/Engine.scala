package org.playground.es.uno

object Engine {

  case class State(started: Boolean)

  val emptyState = State(started = false)

  // should hold all business logic
  def decide(s: State, c: Command): Seq[Event] = c match {
    case StartGame(_, playerCount, _) if playerCount < 2 => throw new IllegalArgumentException("Can't start a game with less than 2 players")
    case StartGame(_, _, _) if s.started => throw new IllegalStateException("Can't start a game twice")
    case StartGame(id, playerCount, firstCard) if playerCount > 1 => Seq(GameStarted(id, playerCount, firstCard))
  }

  // should stay as dumb as possible
  def evolve(s: State, e: Event): State = e match {
    case GameStarted(_, _, _) => s.copy(started = true)
    case _ => s
  }
}
