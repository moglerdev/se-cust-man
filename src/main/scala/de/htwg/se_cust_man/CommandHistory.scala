package de.htwg.se_cust_man


class CommandHistory() {
  var history: Vector[CustomerCommand] = Vector()

  def push(command: CustomerCommand): Unit = {
    history = history :+ command
  }

  def pop(): Option[CustomerCommand] = {
    if (history.nonEmpty) {
      val command = history.last
      history = history.dropRight(1)
      Some(command)
    } else None
  }

  def printHistory(): Unit = {
    history.foreach(println)
  }
}