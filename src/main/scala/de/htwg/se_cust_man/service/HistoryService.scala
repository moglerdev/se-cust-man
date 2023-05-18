package de.htwg.se_cust_man.service


import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import de.htwg.se_cust_man.{History, DB}
import de.htwg.se_cust_man.HistoryChange

// ------------------------------------------------------------------------
// Proxy [3/4]
// https://refactoring.guru/design-patterns/proxy


// https://refactoring.guru/design-patterns/factory-method
object HistoryService {
    def getInstance(apiType: String) = {
        apiType match {
            case "sql" => new HistoryServiceSql()
            case "rest" => new HistoryServiceRest()
        }
    }
}

trait HistoryService {
    def getCurrentId: Int
    def insertHistory(history: History, changes: Vector[HistoryChange]): History
    def getHistorys: Vector[History]
    def getChanges(historyId: Int): Vector[HistoryChange]
    def getHistorysByObjectName(objectName: String): Vector[History]
    def getHistorysByObjectId(objectId: Int): Vector[History]
    def getHistoryById(id: Int): Option[History]
}

class HistoryServiceSql extends HistoryService {
    var conn : Connection = DB.connect

    def getCurrentId : Int = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement();
        val rs = st.executeQuery("SELECT last_value FROM history_id_seq");
        var id = -1
        if (rs.next()) {
            id = rs.getInt(1)
        }
        rs.close();
        st.close();
        id
    }    
    
    def insertHistory(history: History, changes: Vector[HistoryChange]) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("INSERT INTO history(objectname, objectid, action, account_id, time_stamp) VALUES (?, ?, ?, ?)")
        st.setString(1, history.objectName)
        st.setInt(2, history.objectId)
        st.setString(3, history.action.toString())
        st.setInt(4, history.accountId)
        st.setTimestamp(5, history.timestamp)
        st.executeUpdate()
        st.close()
        for(change <- changes) {
            val st2 = conn.prepareStatement("INSERT INTO history_change(history_id, field_name, old_value) VALUES (?, ?, ?)")
            st2.setInt(1, change.historyId)
            st2.setString(2, change.fieldName)
            st2.setString(3, change.oldValue)
            st2.executeUpdate()
            st2.close()
        }
        history
    }

    def getHistorys: Vector[History] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement()
        val rs = st.executeQuery("SELECT * FROM history")
        var historys = Vector[History]()
        while (rs.next()) {
            historys = historys :+ History(id = rs.getInt("id"), objectName = rs.getString("objectname"), objectId = rs.getInt("objectid"), action = rs.getString("action").charAt(0), accountId = rs.getInt("account_id"), timestamp = rs.getTimestamp("time_stamp"))
        }
        rs.close()
        st.close()
        historys
    }
    def getHistoryById(id: Int): Option[History] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM history WHERE id = ?")
        st.setInt(1, id)
        val rs = st.executeQuery()
        var history: Option[History] = None
        if (rs.next()) {
            history = Some(History(id = rs.getInt("id"), objectName = rs.getString("objectname"), objectId = rs.getInt("objectid"), action = rs.getString("action").charAt(0), accountId = rs.getInt("account_id"), timestamp = rs.getTimestamp("time_stamp")))
        }
        rs.close()
        st.close()
        history
    }
    def getHistorysByObjectName(objectName: String): Vector[History] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM history WHERE objectname = ?")
        st.setString(1, objectName)
        val rs = st.executeQuery()
        var historys: Vector[History] = Vector()
        while (rs.next()) {
            historys = historys :+ History(id = rs.getInt("id"), objectName = rs.getString("objectname"), objectId = rs.getInt("objectid"), action = rs.getString("action").charAt(0), accountId = rs.getInt("account_id"), timestamp = rs.getTimestamp("time_stamp"))
        }
        rs.close()
        st.close()
        historys
    }
    def getHistorysByObjectId(objectId: Int): Vector[History] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM history WHERE objectid = ?")
        st.setInt(1, objectId)
        val rs = st.executeQuery()
        var historys: Vector[History] = Vector()
        while (rs.next()) {
            historys = historys :+ History(id = rs.getInt("id"), objectName = rs.getString("objectname"), objectId = rs.getInt("objectid"), action = rs.getString("action").charAt(0), accountId = rs.getInt("account_id"), timestamp = rs.getTimestamp("time_stamp"))
        }
        rs.close()
        st.close()
        historys
    }
    def getChanges(historyId: Int): Vector[HistoryChange] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM history_change WHERE history_id = ?")
        st.setInt(1, historyId)
        val rs = st.executeQuery()
        var changes = Vector[HistoryChange]()
        while (rs.next()) {
            changes = changes :+ HistoryChange(id = rs.getInt("id"), historyId = rs.getInt("history_id"), fieldName = rs.getString("field_name"), oldValue = rs.getString("old_value"))
        }
        rs.close()
        st.close()
        changes
    }
}

class HistoryServiceRest extends HistoryService {
  override def getCurrentId: Int = -1

  override def insertHistory(history: History, changes: Vector[HistoryChange]): History = ???

  override def getChanges(historyId: Int): Vector[HistoryChange] = ???

  override def getHistorysByObjectName(objectName: String): Vector[History] = ???

  override def getHistorys: Vector[History] = ???

  override def getHistoryById(id: Int): Option[History] = ???

  override def getHistorysByObjectId(objectId: Int): Vector[History] = ???

}
