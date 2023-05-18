package de.htwg.se_cust_man.handler


import de.htwg.se_cust_man.handler.{ChainHandler, ChainRequest, ChainResponse}
import de.htwg.se_cust_man.Account
import de.htwg.se_cust_man.Customer
import de.htwg.se_cust_man.service.CustomerService
import de.htwg.se_cust_man.Setting

class InsertCustomerRequest(override val session: Option[Account], val customer: Customer) extends ChainRequest(session)
class UpdateCustomerRequest(override val session: Option[Account], val customer: Customer) extends ChainRequest(session)
class DeleteCustomerRequest(override val session: Option[Account], val customer: Customer) extends ChainRequest(session)
class GetAllCustomersRequest(override val session: Option[Account]) extends ChainRequest(session)
class GetByIdCustomerRequest(override val session: Option[Account], val id: Int) extends ChainRequest(session)

class InsertCustomerResponse(val customer: Customer) extends ChainResponse {
    val status = 200
    val message = "Customer inserted"
}

class UpdateCustomerResponse(val customer: Customer) extends ChainResponse {
    val status = 200
    val message = "Customer updated"
}

class DeleteCustomerResponse(val customer: Customer) extends ChainResponse {
    val status = 200
    val message = "Customer deleted"
}

class GetAllCustomersResponse(val customers: Vector[Customer]) extends ChainResponse {
    val status = 200
    val message = "Customers fetched"
}

class GetByIdCustomerResponse(val customer: Option[Customer]) extends ChainResponse {
    val status = 200
    val message = "Customer fetched"
}

class CustomerHandler extends ChainHandler {

    override def handle(request: ChainRequest): ChainResponse = {
        if (request.isInstanceOf[InsertCustomerRequest]) {
            val insertRequest = request.asInstanceOf[InsertCustomerRequest]
            val customer = insertRequest.customer
            val customerService = CustomerService.getInstance(Setting.serviceProvider)
            val insertedCustomer = customerService.insertCustomer(customer)
            InsertCustomerResponse(insertedCustomer)
        } else if (request.isInstanceOf[UpdateCustomerRequest]) {
            val updateRequest = request.asInstanceOf[UpdateCustomerRequest]
            val customer = updateRequest.customer
            val customerService = CustomerService.getInstance(Setting.serviceProvider)
            val updatedCustomer = customerService.updateCustomer(customer)
            UpdateCustomerResponse(updatedCustomer)
        } else if (request.isInstanceOf[DeleteCustomerRequest]) {
            val deleteRequest = request.asInstanceOf[DeleteCustomerRequest]
            val customer = deleteRequest.customer
            val customerService = CustomerService.getInstance(Setting.serviceProvider)
            val deletedCustomer = customerService.removeCustomer(customer)
            DeleteCustomerResponse(customer)
        } else if (request.isInstanceOf[GetAllCustomersRequest]) {
            val getAllRequest = request.asInstanceOf[GetAllCustomersRequest]
            val customerService = CustomerService.getInstance(Setting.serviceProvider)
            val customers = customerService.getCustomers
            GetAllCustomersResponse(customers)
        } else if (request.isInstanceOf[GetByIdCustomerRequest]) {
            val getByIdRequest = request.asInstanceOf[GetByIdCustomerRequest]
            val customerService = CustomerService.getInstance(Setting.serviceProvider)
            val customer = customerService.getCustomerById(getByIdRequest.id)
            GetByIdCustomerResponse(customer)
        } else {
            super.handle(request)
        }
    }
}

object CustomerHandler {
    def createInsertRequest(session: Option[Account], customer: Customer) = {
        new InsertCustomerRequest(session, customer)
    }
    def createUpdateRequest(session: Option[Account], customer: Customer) = {
        new UpdateCustomerRequest(session, customer)
    }
    def createDeleteRequest(session: Option[Account], customer: Customer) = {
        new DeleteCustomerRequest(session, customer)
    }
    def createGetAllRequest(session: Option[Account]) = {
        new GetAllCustomersRequest(session)
    }
    def createGetByIdRequest(session: Option[Account], id: Int) = {
        new GetByIdCustomerRequest(session, id)
    }
}