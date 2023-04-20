package de.htwg.se_cust_man.tui


class IOInvoiceDir(name: String, parent: Option[Directory], children: List[File])
  extends Directory(name, parent, children) {
}


class IOCustomerDir(name: String, parent: Option[Directory], children: List[IOInvoiceDir | IOProjectDir])
  extends Directory(name, parent, children) {


}