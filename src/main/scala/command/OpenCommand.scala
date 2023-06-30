package de.htwg.scm
package command

import de.htwg.scm.state.IState

case class OpenCommand[TModel](state: IState[TModel], customer: TModel) extends ICommand[TModel] {
  private val prev: Option[TModel] = state.get

  override def execute(): IState[TModel] = {
    state.open(customer)
  }

  override def undo(): IState[TModel] = {
    prev match {
      case Some(c) => state.set(c).get
      case None => state.close
    }
  }

  override def redo(): IState[TModel] = {
    execute()
  }
}
