package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.models.DataModel

object DataController {
    def add(dataList: List[DataModel], newData: DataModel): List[DataModel] = dataList.appended(newData)

    def update(dataList: List[DataModel], newData: DataModel): List[DataModel] =
        dataList.map(x => if (x.id == newData.id) newData else x)

    def get(dataList: List[DataModel], id: Int) : Option[DataModel] =
        dataList.find(x => x.id == id);

    def remove(dataList: List[DataModel], id: Int) : List[DataModel] =
        dataList.filter( p => p.id != id )
}
