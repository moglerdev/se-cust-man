package de.htwg.scm
package model

import java.time.LocalDateTime

case class Project(id: Int, customer_id: Int, title: String, description: String)

object Project {
  def empty(customer: Customer): Project = {
    Project(-1, customer.id, "", "")
  }
}