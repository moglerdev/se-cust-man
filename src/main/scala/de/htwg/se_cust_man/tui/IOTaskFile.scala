package de.htwg.se_cust_man.tui

class IOTaskFile(name: String, parent: Option[Directory], content: () => String)
  extends File(name, parent, content) {

}