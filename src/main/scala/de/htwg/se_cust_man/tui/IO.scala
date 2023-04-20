package de.htwg.se_cust_man.tui

case class Input(cmd: String, args: List[String])


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

  def getRelativeDir(dir: Directory, path: String): Option[Directory] = {
    dir.findDir(path)
  }
}

/**
 * Class for Directory
 * @param name Name of the Directory
 * @param parent Parent Directory
 * @param children List of Children (Files and Directories)
 */
class Directory(val name: String, var parent: Option[Directory], var children: List[IOObject]) extends IOObject {

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
          case Some(dir) => dir.findDir(token)
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
  def execute(input: Input): Executed = {
    if (input.cmd == "cd") {
      val dir = getDirs.find(_.name == input.args.head)
      if (dir.isDefined)
        return Executed(input, ExecutedResult.Open, Some(dir.get))
      else
        return Executed(input, ExecutedResult.Failure, Some(this), Some("Directory not found"))
    }
    else if (input.cmd == "ls") {
      if (children.nonEmpty) {
        val sb = new StringBuilder
        children.foreach(child => sb.append(child.getName).append("\n"))
        return Executed(input, ExecutedResult.Success, Some(this), Some(sb.toString))
      }
      else
        return Executed(input, ExecutedResult.Success, Some(this), Some("Directory is empty"))
    }
    Executed(input, ExecutedResult.Unknown, Some(this))
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
    * @param file File or Directory to add
    * @return of the Directory with added child
    */
  def addChild(file: IOObject): Directory = {
    children = file :: children
    this
  }

  /**
    * return Directory with added children
    * @param files List of Files or Directories to add
    * @return of the Directory with added children
    */
  def addChildren(files: List[IOObject]): Directory = {
    children = files ::: children
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
    children.foreach(x => x.setParent(Some(dir)))
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

class CustomerSubDir(name: String, parent: Option[Directory], children: List[IOObject])
  extends Directory(name, parent, children) {

}

class InvoiceDir(name: String, parent: Option[Directory], children: List[File])
  extends CustomerSubDir(name, parent, children) {

}

class ProjectDir(name: String, parent: Option[Directory], children: List[TaskFile])
  extends CustomerSubDir(name, parent, children) {

}

class CustomerDir(name: String, parent: Option[Directory], children: List[CustomerSubDir])
  extends Directory(name, parent, children) {

}

/**
 * Class for File
 * @param name Name of the File
 * @param parent Parent Directory
 * @param content Function to get the content of the File
 */
class File(val name: String, var parent: Option[Directory], var content: () => String)
  extends IOObject {

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

  override def delete: File = {
    throw new UnsupportedOperationException
  }

  /**
   * toString = name
   * @return name of the File
   */
  override def toString: String = name
}


class TaskFile(name: String, parent: Option[Directory], content: () => String)
  extends File(name, parent, content) {

}