package de.htwg.se_cust_man.models

case class Task(id: Long, title: String, content: String)

object Project {
  def fromCSV(csvRow: String): Project = {
    val values = csvRow.split(";")
    Project(values(0).toLong, values(1))
  }
}

case class Project(id: Long, title: String) {
  override def toString: String = s"ID:$id: TITLE:$title"
  def toCSV: String = s"$id;$title"
}