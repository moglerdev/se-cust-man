package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Project
import de.htwg.scm.state.ProjectState
import de.htwg.scm.command.OpenProjectCommand

class OpenProjectCommandSpec extends AnyFlatSpec with Matchers {

  val initialState = ProjectState()
  val project = Project(1, 1, "Project X", "A sample project")

  "An OpenProjectCommand" should "set the project in the state and return true when executed" in {
    val command = OpenProjectCommand(initialState, project)

    val result = command.execute()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(project))
  }

  it should "set the previous project in the state and return true when undone" in {
    val command = OpenProjectCommand(initialState, project)

    command.execute()

    val result = command.undo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(None)
  }

  it should "set the project in the state and return true when redone" in {
    val command = OpenProjectCommand(initialState, project)

    command.execute()
    command.undo()

    val result = command.redo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(project))
  }
}
