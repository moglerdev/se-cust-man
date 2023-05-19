package de.htwg.se_cust_man.builder

import de.htwg.se_cust_man.Customer
import java.util.Date
import de.htwg.se_cust_man.{Task, Project}



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
    def build = Project(-1, title, description, deadline, customer)
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
