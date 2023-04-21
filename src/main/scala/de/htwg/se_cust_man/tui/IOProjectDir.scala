package de.htwg.se_cust_man.tui

object IOProjectDir {
  def makeTask(input: Input, dir: IOProjectDir): Executed = {
    if (input.args.length < 2) return Executed(input, ExecutedResult.Failure, Some(dir), Some("Wrong number of arguments"))

    val name = input.args(1)
    val content = input.args.slice(2, input.args.length).mkString(" ")
    new IOTaskFile(name, Some(dir), () => content)
    Executed(input, ExecutedResult.Success, Some(dir), Some("Customer created"))
  }
}

class IOProjectDir(name: String, parent: Option[IOCustomerDir], children: List[IOTaskFile])
  extends Directory(name, parent, children) {

  /**
   * Constructor for IOProjectDir
   * @param name   Name of the Directory
   * @param parent Parent Directory
   */
  def this(name: String, parent: Option[IOCustomerDir]) = this(name, parent, List())

  /**
   * Constructor for IOProjectDir
   * @param name Name of the Directory
   */
  def this(name: String) = this(name, None, List())

  override def execute(input: Input): Option[Executed] = {
    if (input.cmd == "cd") Some(Directory.changeDir(input, this))
    else if (input.cmd == "ls") Some(Directory.listDir(input, this))
    else if (input.cmd == "make") {
      if (input.args.length < 1) return Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Wrong number of arguments, use 'make <task> <name> <description>'")))
      input.args(0) match {
        case "task" => Some(IOProjectDir.makeTask(input, this))
        case _ => Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Unknown parameter, use <task>")))
      }
    } else if (input.cmd == "read") {
      if (input.args.length < 1) return Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Wrong number of arguments, use 'read <task_name>'")))
      val taskName = input.args(0)
      val task = this.getFiles.find(_.getName == taskName)
      if (task.isEmpty) return Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Task not found")))
      Some(Executed(input, ExecutedResult.Success, Some(this), Some(task.get.getContent)))
    }
    else None
  }

  override def toString: String = {
    "(Project) " + name + "/"
  }
}
