package de.htwg.scm
package command

import state.IState

import store.DBCustomerStore
import scala.util.Failure
import scala.util.Success

// Command for modifying
case class SetCommand[TModel](state: IState[TModel], modified: TModel) extends ICommand[TModel] {
  val prev: Option[TModel] = state.get

  // Executes the command by modifying the customer state
  override def execute(): IState[TModel] = {
    state.set(modified) match
      case Failure(exception) => state.close
      case Success(value) => value
  }

  // Undoes the command by reverting the customer state to its original value
  override def undo(): IState[TModel] = {
    prev match {
      case Some(c) => state.set(modified).get
      case None => state.close
    }
  }

  // Redoes the command by executing it again
  override def redo(): IState[TModel] = {
    execute()
  }
}
