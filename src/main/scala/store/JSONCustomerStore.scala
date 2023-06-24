package de.htwg.scm
package store

import model.{Customer, CustomerBuilder}

import java.io.{File, FileWriter, IOException}
import scala.io.Source

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json, Printer}

class JSONCustomerStore extends ICustomerStore {
  private val jsonFilePath: String = "store/customers.json"
  private val printer: Printer = Printer.spaces2.copy(dropNullValues = true)

  override def create(model: Customer): Int = {
    val newModel = model.copy(id = getLastId + 1)
    val customers = getAll
    val updatedCustomers = customers :+ newModel
    writeCustomersToFile(updatedCustomers)
    newModel.id  // Return the index of the newly created customer
  }

  override def read(id: Int): Option[Customer] = {
    val customers = getAll
    if (id >= 0 && id < customers.length) {
      Some(customers(id))
    } else {
      None
    }
  }

  override def update(id: Int, model: Customer): Int = {
    val customers = getAll
    if (id >= 0 && id < customers.length) {
      val updatedCustomers = customers.updated(id, model)
      writeCustomersToFile(updatedCustomers)
      id
    } else {
      -1  // Invalid ID
    }
  }

  override def delete(id: Int): Int = {
    val customers = getAll
    if (id >= 0 && id < customers.length) {
      val updatedCustomers = customers.filterNot(_.id == id)
      writeCustomersToFile(updatedCustomers)
      id
    } else {
      -1  // Invalid ID
    }
  }

  override def delete(model: Customer): Int = delete(model.id)

  override def getAll: List[Customer] = {
    try {
      val jsonFile = new File(jsonFilePath)
      if (jsonFile.exists()) {
        val file = Source.fromFile(jsonFile)
        val jsonString = file.mkString
        decode[List[Customer]](jsonString) match {
          case Right(customers) => customers
          case Left(error) =>
            println("Error parsing JSON file: " + error.getMessage)
            List.empty
        }
      } else {
        List.empty
      }
    } catch {
      case e: IOException =>
        println("Error reading JSON file: " + e.getMessage)
        List.empty
    }
  }

  private def writeCustomersToFile(customers: List[Customer]): Unit = {
    try {
      val jsonString = customers.asJson.printWith(printer)
      val fileWriter = new FileWriter(jsonFilePath)
      fileWriter.write(jsonString)
      fileWriter.close()
    } catch {
      case e: IOException =>
        println("Error writing to JSON file: " + e.getMessage)
    }
  }

  override def getLastId: Int = {
    val customers = getAll
    if (customers.isEmpty) {
      0
    } else {
      customers.last.id
    }
  }
}
