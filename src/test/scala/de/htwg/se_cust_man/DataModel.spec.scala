package de.htwg.se_cust_man

import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import de.htwg.se_cust_man.*
import de.htwg.se_cust_man.controllers.DataController

import java.util.Date
import de.htwg.se_cust_man.models.DataModel

import scala.List

case class TestModel(id: Long, name: String) extends DataModel

class DataModelSpec extends AnyFlatSpec with should.Matchers {
    "DataController.add" should "return list with required values" in {
        val dataModel = TestModel(122, "Test")
        val dataModel2 = TestModel(222, "Google")

        // return List with one DataModel
        val lo = DataController.add(List(), dataModel)
        lo should be (List(dataModel))
        // return List with two DataModels
        val lt = DataController.add(List(dataModel), dataModel2)
        lt should be (List(dataModel, dataModel2))
    }

    "DataController.update" should "return list with updated values" in {
        val dataModel = TestModel(111, "Hello")
        val dataModel2 = TestModel(222, "Google")

        val toUpdate = TestModel(111, "222")

        val ourList = List(dataModel, dataModel2)

        val updatedList = DataController.update(ourList, toUpdate)
        updatedList should be (List(toUpdate, dataModel2))
    }

    "DataController.get" should "return required model" in {
        val dataModel = TestModel(111, "Hello")
        val dataModel2 = TestModel(222, "Google")

        val ourList = List(dataModel, dataModel2)

        val requiredModel = DataController.get(ourList, 111)
        requiredModel should be (Some(dataModel))

        val requiredModel2 = DataController.get(ourList, 222)
        requiredModel2 should be (Some(dataModel2))

        val requiredModel3 = DataController.get(ourList, 333)
        requiredModel3 should be (None)
    }

    "DataController.remove" should "return list without removed element" in {
        val dataModel = TestModel(111, "Hello")
        val dataModel2 = TestModel(222, "Google")

        val ourList = List(dataModel, dataModel2)

        val removedList = DataController.remove(ourList, 111)
        removedList should be (List(dataModel2))

        val removeSecond = DataController.remove(removedList, 222)
        removeSecond should be(List())

        val removeNothing = DataController.remove(removeSecond, 2222)
        removeNothing should be(List())

    }
}