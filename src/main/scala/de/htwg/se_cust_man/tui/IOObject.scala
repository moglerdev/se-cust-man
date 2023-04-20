package de.htwg.se_cust_man.tui

case class Input(cmd: String, args: Vector[String])


enum ExecutedResult {
  case Success
  case Failure
  case Open
  case Unknown
  case Exit
}

case class Executed(input: Input, result: ExecutedResult, dir: Option[Directory], msg: Option[String] = None)

/**
 * Trait Class for IO Object for File and Directory
 */
trait IOObject {
  def getName: String
  def getPath: String
  def setParent(dir: Option[Directory]): IOObject
  def getParent: Option[Directory]
  def isDirectory: Boolean
  def rename(newName: String): IOObject
  def delete: IOObject
}

object Directory {
  private val SEPARATOR = "/"

  /**
   * change the current Directory (virtual)
   * @param input Input
   * @param dir Current Directory
   * @return Executed with new Directory
   */
  def changeDir(input: Input, dir: Directory): Executed = {
    dir.findDir(input.args(0)) match {
      case Some(dir) => Executed(input, ExecutedResult.Open, Some(dir))
      case None => Executed(input, ExecutedResult.Failure, Some(dir), Some("Directory not found"))
    }
  }

  /**
   * list the content of the current Directory
   * @param input Input
   * @param dir Current Directory
   * @return Executed with the content of the Directory
   */
  def listDir(input: Input, dir: Directory): Executed = {
    if (dir.children.nonEmpty) {
      val sb = new StringBuilder
      dir.children.foreach(child => sb.append(child.getName).append("\n"))
      sb.deleteCharAt(sb.length - 1)
      Executed(input, ExecutedResult.Success, Some(dir), Some(sb.toString))
    }
    else
      Executed(input, ExecutedResult.Success, Some(dir), Some("Directory is empty"))
  }

  def makeCustomer(input: Input, dir: Directory): Executed = {
    if (input.args.length > 1) {
      val name = input.args(1)
      new IOCustomerDir(name, Some(dir))
      Executed(input, ExecutedResult.Success, Some(dir), Some("Customer created"))
    }
    else
      Executed(input, ExecutedResult.Failure, Some(dir), Some("Wrong number of arguments"))
  }

}

/**
 * Class for Directory
 * @param name Name of the Directory
 * @param parent Parent Directory
 * @param children List of Children (Files and Directories)
 */
class Directory(val name: String, var parent: Option[Directory], var children: List[IOObject]) extends IOObject {
  if(parent.isDefined) parent.get.addChild(this)

  /**
   * Constructor for Directory
   * @param name Name of the Directory
   * @param parent Parent Directory
   */
  def this(name: String, parent: Option[Directory]) = this(name, parent, List())

  /**
   * Constructor for Directory
   * @param name Name of the Directory
   */
  def this(name: String) = this(name, None, List())

  /**
   * get the name of the Directory
   * @return Name of the Directory
   */
  override def getName: String = name

  /**
   * check if the Directory is a Directory
   * @return true if it is a Directory
   */
  override def isDirectory: Boolean = true

  /**
   * get the path of the Directory
   * @return Path of the Directory
   */
  def getPath: String = {
    val separator = "/"
    val parentPath = parent.map(_.getPath).getOrElse("")
    if (parentPath.isEmpty) name
    else parentPath + separator + name
  }

  /**
   * find a Directory in the Directory
   * @param path Name of the Directory
   * @return Option of the Directory (None if not found)
   */
  def findDir(path: String): Option[Directory] = {
    if (path == ".") return Some(this)
    else if (path == "..") return parent
    val tokens = path.split(Directory.SEPARATOR).toList
    tokens.foldLeft(Option(this))((currentDir, token) => {
      if (currentDir.isEmpty) None
      else if (token == ".") currentDir
      else if (token == "..") currentDir match
          case Some(dir) => dir.parent
          case None => None
      else currentDir match {
          case Some(xx) => xx.getDirs.find(_.getName == token)
          case None => None
        }
    })
  }

  /**
   * find a File in the Directory
   * @param path Name of the File
   * @return Option of the File (None if not found)
   */
  def findFile(path: String): Option[File] = {
    getFiles.find(_.getName == path)
  }

  /**
   * execute a command
   * @param input Input
   * @return Executed
   */
  def execute(input: Input): Option[Executed] = {
    if (input.cmd == "cd") Some(Directory.changeDir(input, this))
    else if (input.cmd == "ls") Some(Directory.listDir(input, this))
    else if (input.cmd == "make" ) {
      if (input.args.length < 1) return Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Wrong number of arguments, use 'make <customer> <name>'")))
      input.args(0) match {
        case "customer" => Some(Directory.makeCustomer(input, this))
        case _ => Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Unknown parameter, use 'customer'")))
      }
    }
    else None
  }

  /**
   * set the parent of the Directory
   * @param parent Parent Directory
   * @return Directory with new parent
   */
  override def setParent(parent: Option[Directory]): Directory = {
    this.parent = parent
    this
  }

  /**
   * get the parent of the Directory
   * @return Option of the parent Directory
   */
  override def getParent: Option[Directory] = this.parent

  /**
   * get all files
   * @return List of Files
   */
  def getFiles: List[File] = children.filter(_.isDirectory == false).map(_.asInstanceOf[File])

  /**
   * get all directories
   * @return List of Directories
   */
  def getDirs: List[Directory] = children.filter(_.isDirectory == true).map(_.asInstanceOf[Directory])

  /**
    * return Directory with added child
    * @param child File or Directory to add
    * @return of the Directory with added child
    */
  def addChild(child: IOObject): Directory = {
    if (this.children.contains(child)) {
      println("Child already exists")
      return this
    }
    children = child :: children
    this
  }

  /**
   * set the children of the Directory
   */
  def setChildren(children: List[IOObject]): Directory = {
    this.children = children
    this
  }

  /**
    * return Directory with added children
    * @param childs List of Files or Directories to add
    * @return of the Directory with added children
    */
  def addChildren(childs: List[IOObject]): Directory = {
    children = childs ::: children
    this
  }

  /**
    * return Directory with removed child
    * @param file File or Directory to remove
    * @return of the Directory with removed child
    */
  def removeChild(file: IOObject): Directory = {
    children = children.filterNot(_ == file)
    this
  }

  /**
    * return Directory with removed children
    * @param files List of Files or Directories to remove
    * @return of the Directory with removed children
    */
  def removeChildren(files: List[IOObject]): Directory = {
    children = children.filterNot(files.contains)
    this
  }

  /**
    * return Directory with new name
    * @param newName new name of the Directory
    * @return of the Directory with new name
    */
  def rename(newName: String): Directory = {
    val dir = new Directory(newName, parent, children)
    parent match {
      case Some(p) => p.removeChild(this).addChild(dir)
      case None => dir
    }
    children.foreach(x => {
      if (x.getParent.get == this) x.setParent(Some(dir))
    })
    dir
  }

  /**
   * NOT IMPLEMENTED!
   * delete the Directory and all its children (Files and Directories)
   * @return Directory
   */
  def delete: Directory = {
    throw new UnsupportedOperationException
  }

  /**
    * toString = name
    * @return name of the Directory
    */
  override def toString: String = name
}



/**
 * Class for File
 * @param name Name of the File
 * @param parent Parent Directory
 * @param content Function to get the content of the File
 */
class File(val name: String, var parent: Option[Directory], var content: () => String)
  extends IOObject {
  if(parent.isDefined) parent.get.addChild(this)

  def this(name: String, parent: Option[Directory], content: String) = this(name, parent, () => content)

  def this(name: String, parent: Option[Directory] = None) = this(name, parent, () => "")

  /**
   * get the content of the File
   * @return Content of the File
   */
  def getContent: String = content()

  /**
   * get the path of the File
   * @return Path of the File
   */
  override def getPath: String = {
    if (parent.isDefined)
      parent.get.getPath + "/" + name
    else
      name
  }

  /**
   * get the parent of the File
   * @return Option of the parent Directory
   */
  override def getParent: Option[Directory] = parent

  /**
   * set the parent of the File
   */
  override def setParent(parent: Option[Directory]): File = {
    if (parent.isDefined)
      this.parent = parent
    this
  }
  /**
   * get the name of the Directory
   *
   * @return Name of the Directory
   */
  override def getName: String = name

  /**
   * check if the Directory is a Directory
   *
   * @return true if it is a Directory
   */
  override def isDirectory: Boolean = false

  override def rename(newName: String): File = {
    File(newName, parent, content)
  }

  /**
   * NOT IMPLEMENTED!
   * delete the File
   * @return deleted File
   */
  override def delete: File = {
    throw new UnsupportedOperationException
  }

  /**
   * toString = name
   * @return name of the File
   */
  override def toString: String = name
}

