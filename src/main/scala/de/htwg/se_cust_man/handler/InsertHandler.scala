package de.htwg.se_cust_man.handler

import de.htwg.se_cust_man.Account


class InsertRequest(
    override val session: Option[Account],
    val objectName: String,
    val objectValue: Any) extends ChainRequest(session)

class InsertResponse(val msg: String = "Inserted") extends ChainResponse {
    val status = 200
    val message = msg
}

class InsertHandler extends ChainHandler {

    
    // TODO: Implement Insert Service
    
    override def handle(request: ChainRequest): ChainResponse = {
        if (request.isInstanceOf[InsertRequest]) {

            val r = request.asInstanceOf[InsertRequest]

            throw new Exception("Not implemented")
        } else {
            super.handle(request)
        }
    }
}
