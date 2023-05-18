package de.htwg.se_cust_man.service


import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import de.htwg.se_cust_man.{Address, DB}

// ------------------------------------------------------------------------
// Proxy [3/4]
// https://refactoring.guru/design-patterns/proxy


trait AddressService {
    def insertAddress(address: Address): Address
    def updateAddress(address: Address): Address
    def getAddresss: Vector[Address]
    def getAddressById(id: Int): Option[Address]
    def removeAddress(address: Address): Boolean

}

class AddressServiceSql extends AddressService {
    var conn : Connection = DB.connect
    def insertAddress(address: Address) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("INSERT INTO address(street, zip, city, iso_code) VALUES (?, ?, ?, ?)");
        st.setString(1, address.street);
        st.setString(2, address.zip);
        st.setString(3, address.city);
        st.setString(4, address.isoCode);
        st.executeUpdate();
        st.close();
        address
    }

    def updateAddress(address: Address) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("UPDATE address SET street = ?, zip = ?, city = ?, iso_code = ? WHERE id = ?");
        st.setString(1, address.street);
        st.setString(2, address.zip);
        st.setString(3, address.city);
        st.setString(4, address.isoCode);
        st.setInt(5, address.id);
        st.executeUpdate();
        st.close();
        address
    }

    def getAddresss: Vector[Address] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement();
        val rs = st.executeQuery("SELECT * FROM address");
        var addresss = Vector[Address]()
        while (rs.next()) {
            addresss = addresss :+ Address(rs.getInt("id"), rs.getString("street"), rs.getString("zip"), rs.getString("city"), rs.getString("iso_code"))
        }
        rs.close();
        st.close();
        addresss
    }
    def getAddressById(id: Int): Option[Address] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM address WHERE id = ?");
        st.setInt(1, id);
        val rs = st.executeQuery();
        var address: Option[Address] = None
        if (rs.next()) {
            address = Some(Address(rs.getInt("id"), rs.getString("street"), rs.getString("zip"), rs.getString("city"), rs.getString("iso_code")))
        }
        rs.close();
        st.close();
        address
    }
    def removeAddress(address: Address): Boolean = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("DELETE FROM address WHERE id = ?");
        st.setInt(1, address.id);
        val rs = st.executeUpdate();
        st.close();
        rs > 0
    }
}

class AddressServiceRest extends AddressService {
    def insertAddress(address: Address) = {
        address
    }
    def updateAddress(address: Address) = {
        address
    }
    def deleteAddress(address: Address) = {
        address
    }
    def getAddresss: Vector[Address] = {
        Vector()
    }
    def getAddressById(id: Int): Option[Address] = {
        None
    }
    def getAddressByName(name: String): Option[Address] = {
        None
    }
    def removeAddress(address: Address): Boolean = {
        false
    }
}
