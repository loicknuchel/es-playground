package org.playground.es.uno.domain

sealed trait Card
case class Digit(color: Color, value: Int) extends Card
case class KickBack(color: Color) extends Card
