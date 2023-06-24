package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Task
import de.htwg.scm.state.TaskState
import de.htwg.scm.command.SetTaskCommand

class SetTaskCommandSpec extends AnyFlatSpec with Matchers {

  val initialState = TaskState()
  val initialTask = Task(1, 1, "Task A", "A sample task")

  "A SetTaskCommand" should "set the task in the state with the provided values and return true when executed" in {
    val command = SetTaskCommand(initialState, 2, Some("New Title"), Some("New Description"))

    initialState.set(Some(initialTask))

    val result = command.execute()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(Task(1, 2, "New Title", "New Description")))
  }

  it should "set the previous task in the state and return true when undone" in {
    val command = SetTaskCommand(initialState, 2, Some("New Title"), Some("New Description"))

    initialState.set(Some(initialTask))

    command.execute()

    val result = command.undo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(initialTask))
  }

  it should "set the task in the state with the provided values and return true when redone" in {
    val command = SetTaskCommand(initialState, 2, Some("New Title"), Some("New Description"))

    initialState.set(Some(initialTask))

    command.execute()
    command.undo()

    val result = command.redo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(Task(1, 2, "New Title", "New Description")))
  }
}
