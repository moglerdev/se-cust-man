package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Task
import de.htwg.scm.state.TaskState
import de.htwg.scm.command.OpenTaskCommand

class OpenTaskCommandSpec extends AnyFlatSpec with Matchers {

  val initialState = TaskState()
  val task = Task(1, 1, "Task A", "A sample task")

  "An OpenTaskCommand" should "set the task in the state and return true when executed" in {
    val command = OpenTaskCommand(initialState, task)

    val result = command.execute()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(task))
  }

  it should "set the previous task in the state and return true when undone" in {
    val command = OpenTaskCommand(initialState, task)

    command.execute()

    val result = command.undo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(None)
  }

  it should "set the task in the state and return true when redone" in {
    val command = OpenTaskCommand(initialState, task)

    command.execute()
    command.undo()

    val result = command.redo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(task))
  }
}
