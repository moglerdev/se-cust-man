package de.htwg.se_cust_man


class CommandHistory() {
  var history: Vector[CustomerCommand] = Vector()

  def add(command: CustomerCommand): Unit = {
    history = history.appended(command)
  }

  def undo(): Option[CustomerCommand] = {
    if (history.nonEmpty) {
      while(!history.head.hasBackup) {
        history = history.tail
      }
      Some(history.head)
    } else None
  }

  def printHistory(): Unit = {
    history.foreach(println)
  }
}