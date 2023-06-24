import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import de.htwg.scm.controller.ModelController
import de.htwg.scm.store.IStore

class ModelControllerSpec extends AnyFlatSpec with Matchers {

  // Mock store implementation for testing
  class MockStore[TModel] extends IStore[TModel] {
    private var models: List[TModel] = List.empty

    def create(item: TModel): Int = {
      models = item :: models
      1 // Simulate successful creation
    }

    def update(id: Int, item: TModel): Int = {
      models = models.map {
        case m if m == item => item
        case m => m
      }
      1 // Simulate successful update
    }

    def delete(item: TModel): Int = {
      models = models.filterNot(_ == item)
      1 // Simulate successful deletion
    }

    def read(id: Int): Option[TModel] = models.lift(id)

    def getAll: List[TModel] = models
  }

  "A ModelController" should "add a model" in {
    // Create a test model
    val model = "TestModel"

    // Create a mock store
    val store = new MockStore[String]

    // Create a ModelController with the mock store
    val controller = new ModelController[String](store) {}

    // Add the model
    val result = controller.add(model)

    // Verify that the model was added
    result should be(true)
    controller.getAll should contain only model
  }

  it should "remove a model" in {
    // Create a test model
    val model = "TestModel"

    // Create a mock store
    val store = new MockStore[String]
    store.create(model)

    // Create a ModelController with the mock store
    val controller = new ModelController[String](store) {}

    // Remove the model
    val result = controller.remove(model)

    // Verify that the model was removed
    result should be(true)
    controller.getAll should be(empty)
  }

  it should "update a model" in {
    // Create a test model
    val model = "TestModel"

    // Create a mock store
    val store = new MockStore[String]
    store.create(model)

    // Create a ModelController with the mock store
    val controller = new ModelController[String](store) {}

    // Update the model
    val updatedModel = "UpdatedModel"
    val result = controller.update(0, updatedModel)

    // Verify that the model was updated
    result should be(true)
    controller.getAll should contain only updatedModel
  }

  it should "get all models" in {
    // Create test models
    val model1 = "Model 1"
    val model2 = "Model 2"

    // Create a mock store
    val store = new MockStore[String]
    store.create(model1)
    store.create(model2)

    // Create a ModelController with the mock store
    val controller = new ModelController[String](store) {}

    // Get all models
    val allModels = controller.getAll

    // Verify that all models are retrieved
    allModels should contain theSameElementsAs List(model1, model2)
  }

  it should "get a model by ID" in {
    // Create test models
    val model1 = "Model 1"
    val model2 = "Model 2"

    // Create a mock store
    val store = new MockStore[String]
    store.create(model1)
    store.create(model2)

    // Create a ModelController with the mock store
    val controller = new ModelController[String](store) {}

    // Get a model by ID
    val retrievedModel = controller.get(1)

    // Verify that the correct model is retrieved
    retrievedModel should be(Some(model2))
  }
}
