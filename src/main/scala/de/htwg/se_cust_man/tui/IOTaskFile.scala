package de.htwg.se_cust_man.tui

/**
 * Class for IOTaskFile
 * @param name Name of the File
 * @param parent Parent Directory
 * @param content Function to get the content of the File
 */
class IOTaskFile(name: String, parent: Option[IOProjectDir], content: () => String)
  extends File(name, parent, content) {


  override def toString: String = {
    "(Task) " + name + ".txt"
  }
}