package de.htwg.se_cust_man.service


import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import de.htwg.se_cust_man.{Project, Task, DB}

// ------------------------------------------------------------------------
// Proxy [3/4]
// https://refactoring.guru/design-patterns/proxy


// https://refactoring.guru/design-patterns/factory-method
object ProjectService {
    def getInstance(apiType: String) = {
        apiType match {
            case "sql" => new ProjectServiceSql()
            case "test" => new ProjectServiceTest()
        }
    }
}

trait ProjectService {
    def getCurrentId: Int
    def insertProject(project: Project, tasks: Vector[Task]): Project
    def insertTask(task: Task): Task
    def updateProject(project: Project): Project
    def updateTask(task: Task): Task
    def getProjects: Vector[Project]
    def getProjectById(id: Int): Option[Project]
    def removeProject(project: Project): Boolean
    def removeTask(task: Task): Boolean
}

class ProjectServiceSql extends ProjectService {

    var conn : Connection = DB.connect

    def getCurrentId : Int = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement();
        val rs = st.executeQuery("SELECT last_value FROM project_id_seq");
        var id = -1
        if (rs.next()) {
            id = rs.getInt(1)
        }
        rs.close();
        st.close();
        id
    }

    def insertProject(project: Project, tasks: Vector[Task]) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("INSERT INTO project(title, description, deadline, customer_id) VALUES (?, ?, ?, ?)");
        st.setString(1, project.title);
        st.setString(2, project.description);
        st.setDate(3, new java.sql.Date(project.deadline.getTime()));
        st.setInt(4, project.customerId);
        st.executeUpdate();
        st.close();
        for(task <- tasks) {
            insertTask(task)
        }
        project
    }

    def insertTask(task: Task): Task = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("INSERT INTO task(title, description, estimated_time, required_time, project_id) VALUES (?, ?, ?, ?, ?)");
        st.setString(1, task.title);
        st.setString(2, task.description);
        st.setDouble(3, task.estimatedTime);
        st.setDouble(4, task.requiredTime);
        st.setInt(5, task.projectId);
        st.executeUpdate();
        st.close();
        task
    }

    def updateProject(project: Project) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("UPDATE project SET title = ?, description = ?, deadline = ?, customer_id = ? WHERE id = ?");
        st.setString(1, project.title);
        st.setString(2, project.description);
        st.setDate(3, new java.sql.Date(project.deadline.getTime()));
        st.setInt(4, project.customerId);
        st.setInt(5, project.id);
        st.executeUpdate();
        st.close();
        project
    }
    
    def updateTask(task: Task): Task = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("UPDATE task SET title = ?, description = ?, estimated_time = ?, required_time = ?, project_id = ? WHERE id = ?");
        st.setString(1, task.title);
        st.setString(2, task.description);
        st.setDouble(3, task.estimatedTime);
        st.setDouble(4, task.requiredTime);
        st.setInt(5, task.projectId);
        st.setInt(6, task.id);
        st.executeUpdate();
        st.close();
        task
    }

    def getProjects: Vector[Project] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement();
        val rs = st.executeQuery("SELECT * FROM project");
        var projects = Vector[Project]()
        while (rs.next()) {
            projects = projects :+ Project(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getDate("deadline"), rs.getInt("customer_id"))
        }
        rs.close();
        st.close();
        projects
    }

    def getProjectById(id: Int): Option[Project] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM project WHERE id = ?");
        st.setInt(1, id);
        val rs = st.executeQuery();
        var project: Option[Project] = None
        while (rs.next()) {
            project = Some(Project(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getDate("deadline"), rs.getInt("customer_id")))
        }
        rs.close();
        st.close();
        project
    }

    def removeProject(project: Project): Boolean = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("DELETE FROM project WHERE id = ?");
        st.setInt(1, project.id);
        val rs = st.executeUpdate();
        st.close();
        rs > 0
    }

    def removeTask(task: Task): Boolean = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("DELETE FROM task WHERE id = ?");
        st.setInt(1, task.id);
        val rs = st.executeUpdate();
        st.close();
        rs > 0
    }
}

class ProjectServiceTest extends ProjectService {

  override def getCurrentId: Int = -1

  override def removeTask(task: Task): Boolean = ???

  override def updateProject(project: Project): Project = ???

  override def removeProject(project: Project): Boolean = ???

  override def insertProject(project: Project, tasks: Vector[Task]): Project = ???

  override def getProjects: Vector[Project] = ???

  override def getProjectById(id: Int): Option[Project] = ???

  override def updateTask(task: Task): Task = ???

  override def insertTask(task: Task): Task = ???

}
