package de.htwg.scm
package controller

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.mockito.MockitoSugar
import store.IStore

import org.mockito.Mockito.{reset, times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.WordSpec

class ModelControllerSpec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  // Create a mock store
  val mockStore: IStore[Model] = mock[IStore[Model]]

  // Create an instance of the ModelController with the mock store
  val modelController: ModelController[Model] = new ModelController[Model](mockStore) {}

  // Define the Model class for testing purposes
  case class Model(id: Int, name: String)

  // Test data
  val testModel: Model = Model(1, "Test Model")
  val updatedModel: Model = Model(1, "Updated Model")

  override def beforeEach(): Unit = {
    // Reset the mock store before each test
    reset(mockStore)
  }

  "ModelController" should {

    "add a model successfully" in {
      // Set up the mock store behavior
      when(mockStore.create(testModel)).thenReturn(1)

      // Call the add method
      val result = modelController.add(testModel)

      // Verify the mock store interactions
      verify(mockStore, times(1)).create(testModel)

      // Verify the result
      result should be(true)
    }

    "remove a model successfully" in {
      // Set up the mock store behavior
      when(mockStore.delete(testModel)).thenReturn(1)

      // Call the remove method
      val result = modelController.remove(testModel)

      // Verify the mock store interactions
      verify(mockStore, times(1)).delete(testModel)

      // Verify the result
      result should be(true)
    }

    "update a model successfully" in {
      // Set up the mock store behavior
      when(mockStore.update(testModel.id, updatedModel)).thenReturn(1)

      // Call the update method
      val result = modelController.update(testModel.id, updatedModel)

      // Verify the mock store interactions
      verify(mockStore, times(1)).update(testModel.id, updatedModel)

      // Verify the result
      result should be(true)
    }

    "get all models" in {
      // Set up the mock store behavior
      val expectedModels = List(testModel)
      when(mockStore.getAll).thenReturn(expectedModels)

      // Call the getAll method
      val models = modelController.getAll

      // Verify the mock store interactions
      verify(mockStore, times(1)).getAll

      // Verify the result
      models should be(expectedModels)
    }

    "get a model by ID" in {
      // Set up the mock store behavior
      when(mockStore.read(testModel.id)).thenReturn(Some(testModel))

      // Call the get method
      val result = modelController.get(testModel.id)

      // Verify the mock store interactions
      verify(mockStore, times(1)).read(testModel.id)

      // Verify the result
      result should be(Some(testModel))
    }
  }
}
