package de.htwg.se_cust_man.builder

import java.sql.Timestamp
import de.htwg.se_cust_man.{History, HistoryChange}


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
