package de.htwg.se_cust_man.service


import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import de.htwg.se_cust_man.{Customer, DB}

// ------------------------------------------------------------------------
// Proxy [3/4]
// https://refactoring.guru/design-patterns/proxy


// https://refactoring.guru/design-patterns/factory-method
object CustomerService {
    def getInstance(apiType: String) = {
        apiType match {
            case "sql" => new CustomerServiceSql()
            case "rest" => new CustomerServiceRest()
        }
    }
}

trait CustomerService {
    def insertCustomer(customer: Customer): Customer
    def updateCustomer(customer: Customer): Customer
    def getCustomers: Vector[Customer]
    def getCustomerById(id: Int): Option[Customer]
    def getCustomerByName(name: String): Option[Customer]
    def removeCustomer(customer: Customer): Boolean

}

class CustomerServiceSql extends CustomerService {
    var conn : Connection = DB.connect
    def insertCustomer(customer: Customer) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("INSERT INTO customer(name, address_id, email, phone) VALUES (?, ?, ?, ?)");
        st.setString(1, customer.name);
        st.setInt(2, customer.addressId);
        st.setString(3, customer.email);
        st.setString(4, customer.phone);
        st.executeUpdate();
        st.close();
        customer
    }

    def updateCustomer(customer: Customer) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("UPDATE customer SET name = ?, address_id = ?, email = ?, phone = ? WHERE id = ?");
        st.setString(1, customer.name);
        st.setInt(2, customer.addressId);
        st.setString(3, customer.email);
        st.setString(4, customer.phone);
        st.setInt(5, customer.id);
        st.executeUpdate();
        st.close();
        customer
    }

    def getCustomers: Vector[Customer] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement();
        val rs = st.executeQuery("SELECT * FROM customer");
        var customers = Vector[Customer]()
        while (rs.next()) {
            customers = customers :+ Customer(rs.getInt("id"), rs.getString("name"), rs.getInt("address_id"), rs.getString("email"), rs.getString("phone"))
        }
        rs.close();
        st.close();
        customers
    }
    def getCustomerById(id: Int): Option[Customer] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM customer WHERE id = ?");
        st.setInt(1, id);
        val rs = st.executeQuery();
        var customer: Option[Customer] = None
        if (rs.next()) {
            customer = Some(Customer(rs.getInt("id"), rs.getString("name"), rs.getInt("address_id"), rs.getString("email"), rs.getString("phone")))
        }
        rs.close();
        st.close();
        customer
    }
    def getCustomerByName(name: String): Option[Customer] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM customer WHERE name LIKE ?");
        st.setString(1, "%" + name + "%");
        val rs = st.executeQuery();
        var customer: Option[Customer] = None
        if (rs.next()) {
            customer = Some(Customer(rs.getInt("id"), rs.getString("name"), rs.getInt("address_id"), rs.getString("email"), rs.getString("phone")))
        }
        rs.close();
        st.close();
        customer
    }
    def removeCustomer(customer: Customer): Boolean = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("DELETE FROM customer WHERE id = ?");
        st.setInt(1, customer.id);
        val rs = st.executeUpdate();
        st.close();
        rs > 0
    }
}

class CustomerServiceRest extends CustomerService {
    def insertCustomer(customer: Customer) = {
        customer
    }
    def updateCustomer(customer: Customer) = {
        customer
    }
    def deleteCustomer(customer: Customer) = {
        customer
    }
    def getCustomers: Vector[Customer] = {
        Vector()
    }
    def getCustomerById(id: Int): Option[Customer] = {
        None
    }
    def getCustomerByName(name: String): Option[Customer] = {
        None
    }
    def removeCustomer(customer: Customer): Boolean = {
        false
    }
}
