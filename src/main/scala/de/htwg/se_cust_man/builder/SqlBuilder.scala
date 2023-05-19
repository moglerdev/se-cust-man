package de.htwg.se_cust_man.builder

class SqlBuilder extends Builder[String] {


    def setTableName(tableName: String): SqlBuilder = {
        this
    }
    def addField(fields: Vector[String]): SqlBuilder = {
        this
    }



    override def build: String = ???

    override def reset: Builder[String] = ???


}