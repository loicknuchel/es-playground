package org.playground.es.uno.domain

sealed trait Card {
  val color: Color

  def isSimilar(other: Card): Boolean
}

case class Digit(color: Color, value: Int) extends Card {
  def isSimilar(other: Card): Boolean = other match {
    case Digit(c, v) => c == color || v == value
    case KickBack(c) => c == color
  }
}

case class KickBack(color: Color) extends Card {
  def isSimilar(other: Card): Boolean =
    other.color == color
}
