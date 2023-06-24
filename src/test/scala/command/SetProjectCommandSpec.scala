package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Project
import de.htwg.scm.state.ProjectState
import de.htwg.scm.command.SetProjectCommand

class SetProjectCommandSpec extends AnyFlatSpec with Matchers {

  val initialState = ProjectState()
  val initialProject = Project(1, 1, "Project A", "A sample project")

  "A SetProjectCommand" should "set the project in the state with the provided values and return true when executed" in {
    val command = SetProjectCommand(initialState, 2, Some("New Title"), Some("New Description"))

    initialState.set(Some(initialProject))

    val result = command.execute()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(Project(1, 2, "New Title", "New Description")))
  }

  it should "set the previous project in the state and return true when undone" in {
    val command = SetProjectCommand(initialState, 2, Some("New Title"), Some("New Description"))

    initialState.set(Some(initialProject))

    command.execute()

    val result = command.undo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(initialProject))
  }

  it should "set the project in the state with the provided values and return true when redone" in {
    val command = SetProjectCommand(initialState, 2, Some("New Title"), Some("New Description"))

    initialState.set(Some(initialProject))

    command.execute()
    command.undo()

    val result = command.redo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(Project(1, 2, "New Title", "New Description")))
  }
}
