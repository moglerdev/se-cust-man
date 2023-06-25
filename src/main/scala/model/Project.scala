package de.htwg.scm
package model

import java.time.LocalDateTime

case class Project(id: Int, customer_id: Int, title: String, description: String)

object Project {
  def empty(customer: Customer): Project = {
    Project(-1, customer.id, "", "")
  }

  def apply(args: String, customer: Customer): Project = {
    val idReg = """-i\s+(\d+)""".r
    val titleReg = """-t\s+([^-]*)""".r
    val descriptionReg = """-d\s+(.*)""".r

    var id: Option[Int] = None
    var title: Option[String] = None
    var description: Option[String] = None

    // Extract values from command line arguments
    idReg.findFirstMatchIn(args).foreach(m => id = Some(m.group(1).toInt))
    titleReg.findFirstMatchIn(args).foreach(m => title = Some(m.group(1)))
    descriptionReg.findFirstMatchIn(args).foreach(m => description = Some(m.group(1)))

    Project(id.getOrElse(-1), customer.id, title.map(_.trim).getOrElse(""), description.map(_.trim).getOrElse(""))
  }
}
