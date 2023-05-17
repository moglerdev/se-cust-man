package de.htwg.se_cust_man

import java.sql.Date
import de.htwg.se_cust_man.Subject
import scala.compiletime.ops.boolean

// ------------------------------------------------------------------------

// Models 

case class Customer(id: Int, name: String, address: String, email: String, phone: String)

case class Project(id: Int, title: String, description: String, deadline: Date)

case class Task(id: Int, title: String, description: String, root: Int)

case class User(id: Int, username: String, email: String, password: String, name: String, group: Int)

enum HistoryAction {
    case Changed, Created, Deleted
}

case class History(id: Int, objectName: String, objectId: Int, action: HistoryAction, preState: String)


// ------------------------------------------------------------------------

// States

trait State {
    def handle[Model](model: Model) : State

    def save: Model
    
    def set(newModel: Model): Model

    def delete(): Model)
}

class EditorContext {
    def request = {

    }
}

// ------------------------------------------------------------------------

// Controller 

class CustomerController extends Subject {

}

class UserController extends Subject {

}

class HistoryController extends Subject {

}

class ProjectController extends Subject {

}