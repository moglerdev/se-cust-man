package de.htwg.se_cust_man.handler

import de.htwg.se_cust_man.service.AddressService
import de.htwg.se_cust_man.Setting
import de.htwg.se_cust_man.Address
import de.htwg.se_cust_man.Account


class InsertAddressRequest(override val session: Option[Account], val address: Address) extends ChainRequest(session)
class UpdateAddressRequest(override val session: Option[Account], val address: Address) extends ChainRequest(session)
class DeleteAddressRequest(override val session: Option[Account], val address: Address) extends ChainRequest(session)
class GetAllAddresssRequest(override val session: Option[Account]) extends ChainRequest(session)
class GetByIdAddressRequest(override val session: Option[Account], val id: Int) extends ChainRequest(session)


class InsertAddressResponse(val address: Address) extends ChainResponse {
    val status = 200
    val message = "Address inserted"
}

class UpdateAddressResponse(val address: Address) extends ChainResponse {
    val status = 200
    val message = "Address updated"
}

class DeleteAddressResponse(val address: Address) extends ChainResponse {
    val status = 200
    val message = "Address deleted"
}

class GetAllAddresssResponse(val addresses: Vector[Address]) extends ChainResponse {
    val status = 200
    val message = "Addresss fetched"
}

class GetByIdAddressResponse(val address: Option[Address]) extends ChainResponse {
    val status = 200
    val message = "Address fetched"
}

class AddressHandler extends ChainHandler {

    override def handle(request: ChainRequest): ChainResponse = {
        if (request.isInstanceOf[InsertAddressRequest]) {
            val insertRequest = request.asInstanceOf[InsertAddressRequest]
            val address = insertRequest.address
            val addressService = AddressService.getInstance(Setting.serviceProvider)
            val insertedAddress = addressService.insertAddress(address)
            InsertAddressResponse(insertedAddress)
        } else if (request.isInstanceOf[UpdateAddressRequest]) {
            val updateRequest = request.asInstanceOf[UpdateAddressRequest]
            val address = updateRequest.address
            val addressService = AddressService.getInstance(Setting.serviceProvider)
            val updatedAddress = addressService.updateAddress(address)
            UpdateAddressResponse(updatedAddress)
        } else if (request.isInstanceOf[DeleteAddressRequest]) {
            val deleteRequest = request.asInstanceOf[DeleteAddressRequest]
            val address = deleteRequest.address
            val addressService = AddressService.getInstance(Setting.serviceProvider)
            val deletedAddress = addressService.removeAddress(address)
            DeleteAddressResponse(address)
        } else if (request.isInstanceOf[GetAllAddresssRequest]) {
            val getAllRequest = request.asInstanceOf[GetAllAddresssRequest]
            val addressService = AddressService.getInstance(Setting.serviceProvider)
            val addresses = addressService.getAddresses
            GetAllAddresssResponse(addresses)
        } else if (request.isInstanceOf[GetByIdAddressRequest]) {
            val getByIdRequest = request.asInstanceOf[GetByIdAddressRequest]
            val addressService = AddressService.getInstance(Setting.serviceProvider)
            val address = addressService.getAddressById(getByIdRequest.id)
            GetByIdAddressResponse(address)
        } else {
            super.handle(request)
        }
    }
}

object AddressHandler {
    def createInsertRequest(session: Option[Account], address: Address) = {
        new InsertAddressRequest(session, address)
    }
    def createUpdateRequest(session: Option[Account], address: Address) = {
        new UpdateAddressRequest(session, address)
    }
    def createDeleteRequest(session: Option[Account], address: Address) = {
        new DeleteAddressRequest(session, address)
    }
    def createGetAllRequest(session: Option[Account]) = {
        new GetAllAddresssRequest(session)
    }
    def createGetByIdRequest(session: Option[Account], id: Int) = {
        new GetByIdAddressRequest(session, id)
    }
}