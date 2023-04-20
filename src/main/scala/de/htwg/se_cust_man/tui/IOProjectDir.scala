package de.htwg.se_cust_man.tui



class IOProjectDir(name: String, parent: Option[Directory], children: List[IOTaskFile])
  extends Directory(name, parent, children) {

}
