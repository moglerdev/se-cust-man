package controller

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

package de.htwg.scm.controller

import store.IStore

class EditorControllerSpec extends AnyFlatSpec with Matchers {

  // Mock store implementation for testing
  class MockStore[TModel] extends IStore[TModel] {
    private var model: TModel = _

    def create(item: TModel): Int = {
      model = item
      1 // Simulate successful creation
    }
    def update(id: Int, item: TModel): Int = {
      model = item
      1 // Simulate successful update
    }
    def delete(item: TModel): Int = {
      model = null.asInstanceOf[TModel]
      1 // Simulate successful deletion
    }
    def read(id: Int): Option[TModel] = Option(model)
    def getAll: List[TModel] = List(model)
  }

  "An EditorController" should "create a new model instance and mark it as dirty" in {
    // Create a test model
    val model = "TestModel"

    // Create a mock store
    val store = new MockStore[String]

    // Create an EditorController with the mock store
    val controller = new EditorController[String](store)

    // Create the model instance
    val result = controller.create(model)

    // Verify that the model was created and marked as dirty
    result should be(true)
    controller.get should be(model)
    controller.isOpen should be(true)
    controller.save should be(false) // Saving without opening should fail
    controller.close should be(true)
  }

  it should "open the editor with the specified ID" in {
    // Create a mock store
    val store = new MockStore[String]

    // Create an EditorController with the mock store
    val controller = new EditorController[String](store)

    // Open the editor with the ID 1
    val result = controller.open(1)

    // Verify that the editor was opened
    result should be(true)
    controller.get should be(null.asInstanceOf[String])
    controller.isOpen should be(true)
    controller.save should be(false) // Saving without a model should fail
    controller.close should be(true)
  }

  it should "update the model instance and mark it as dirty" in {
    // Create a test model
    val model = "TestModel"

    // Create a mock store
    val store = new MockStore[String]

    // Create an EditorController with the mock store
    val controller = new EditorController[String](store)

    // Update the model instance
    val result = controller.update(model)

    // Verify that the model was updated and marked as dirty
    result should be(true)
    controller.get should be(model)
    controller.isOpen should be(true)
    controller.save should be(true) // Saving with an updated model should succeed
    controller.close should be(true)
  }

  it should "close the editor and clear the model instance" in {
    // Create a mock store
    val store = new MockStore[String]

    // Create an EditorController with the mock store
    val controller = new EditorController[String](store)

    // Close the editor
    val result = controller.close

    // Verify that the editor was closed and the model instance was cleared
    result should be(true)
    controller.get should be(null.asInstanceOf[String])
    controller.isOpen should be(false)
    controller.save should be(false) // Saving without a model should fail
  }

}
