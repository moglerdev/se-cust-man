package de.htwg.se_cust_man

import java.sql.Timestamp
import java.security.MessageDigest
import java.sql.Date

// ------------------------------------------------------------------------
// Builder [1/4]
// https://refactoring.guru/design-patterns/builder

trait Builder[Model] {
    def build: Model
    def reset: Builder[Model]
}

class ProjectBuilder extends Builder[Project] {
    var title: String = ""
    var deadline: Date = new Date(0)
    var customer: Int = -1
    var description: String = ""

    def setTitle(projectTitle: String): ProjectBuilder = {
        title = projectTitle
        this
    }
    def setCustomer(forCustomer: Int): ProjectBuilder = {
        customer = forCustomer
        this
    }
    def setCustomer(myCustomer: Customer): ProjectBuilder = {
        customer = myCustomer.id
        this
    }
    def setDescription(specificDescription: String): ProjectBuilder = {
        description = specificDescription
        this
    }
    def setDeadline(untilDeadline: Date): ProjectBuilder = {
        deadline = untilDeadline
        this
    }
    def build = Project(-1, title, "", deadline, customer)
    def reset = new ProjectBuilder
}

class TaskBuilder extends Builder[Vector[Task]] {
    var tasks: Vector[Task] = Vector()
    var projectId: Int = -1

    def setProjectId(projectId: Int): TaskBuilder = {
        this.projectId = projectId
        tasks = tasks.map(_.copy(projectId = projectId))
        this
    }

    def addTask(taskTitle: String, taskDescription: String = "", taskEstimatedTime: Double = 0): TaskBuilder = {
        tasks = tasks :+ Task(-1, taskTitle, taskDescription, taskEstimatedTime, 0, projectId)
        this
    }
    def addTask(task: Task): TaskBuilder = {
        tasks = tasks :+ task.copy(id = -1, projectId = projectId)
        this
    }

    def build = tasks
    def reset = new TaskBuilder
}

class AccountBuilder extends Builder[Account]{
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var name: String = ""

    def setUsername(username: String): AccountBuilder = {
        this.username = username
        this
    }
    def setEmail(email: String): AccountBuilder = {
        this.email = email
        this
    }
    def setPassword(clearPassword: String): AccountBuilder = {
        this.password = MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8")).map("%02x".format(_)).mkString
        this
    }
    def setName(name: String): AccountBuilder = {
        this.name = name
        this
    }
    def build = Account(-1, username, email, password, name)
    def reset = new AccountBuilder
}

class HistoryBuilder extends Builder[History] {
    var objectName: String = ""
    var objectId: Int = -1
    var action: Char = ' '
    var accountId: Int = -1
    var timestamp: Timestamp = new Timestamp(0)

    def setObjectName(objectName: String): HistoryBuilder = {
        this.objectName = objectName
        this
    }
    def setObjectId(objectId: Int): HistoryBuilder = {
        this.objectId = objectId
        this
    }
    def setAction(action: Char): HistoryBuilder = {
        this.action = action
        this
    }
    def setAccountId(accountId: Int): HistoryBuilder = {
        this.accountId = accountId
        this
    }
    def setTimestamp(timestamp: Timestamp): HistoryBuilder = {
        this.timestamp = timestamp
        this
    }
    def build = History(-1, objectName, objectId, action, accountId, timestamp)
    def reset = new HistoryBuilder
}

class HistoryChangeBuilder extends Builder[Vector[HistoryChange]] {
    var changes: Vector[HistoryChange] = Vector()
    var historyId: Int = -1

    def setHistoryId(historyId: Int): HistoryChangeBuilder = {
        this.historyId = historyId
        changes = changes.map(_.copy(historyId = historyId))
        this
    }

    def addChange(fieldName: String, oldValue: String): HistoryChangeBuilder = {
        changes = changes :+ HistoryChange(id = -1, historyId = historyId, fieldName = fieldName, oldValue = oldValue) 
        this
    }
    def build = changes
    def reset = new HistoryChangeBuilder
}

class CustomerBuilder extends Builder[Customer] {
    var name: String = ""
    var address: Int = -1
    var email: String = ""
    var phone: String = ""

    def setName(name: String): CustomerBuilder = {
        this.name = name
        this
    }
    def setAddress(address: Int): CustomerBuilder = {
        this.address = address
        this
    }
    def setEmail(email: String): CustomerBuilder = {
        this.email = email
        this
    }
    def setPhone(phone: String): CustomerBuilder = {
        this.phone = phone
        this
    }
    def build = Customer(-1, name, address, email, phone)
    def reset = new CustomerBuilder
}

class AddressBuilder extends Builder[Address] {
    var street: String = ""
    var zip: String = ""
    var city: String = ""
    var isoCode: String = "de-DE"

    def setStreet(street: String): AddressBuilder = {
        this.street = street
        this
    }
    def setZip(zip: String): AddressBuilder = {
        this.zip = zip
        this
    }
    def setCity(city: String): AddressBuilder = {
        this.city = city
        this
    }
    def setIsoCode(isoCode: String): AddressBuilder = {
        this.isoCode = isoCode
        this
    }
    def build = Address(-1, street, zip, city, isoCode)
    def reset = new AddressBuilder
}
