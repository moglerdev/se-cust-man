import React from "react";
import CustomerView from "../components/CustomerView";
import { Customer } from "../model";
import Card from "../components/Card";
import Anchor from "../components/Anchor";

const CustomersPage: React.FC = () => {
  // Example customer data
  const customers: Array<Customer> = [
    {
      id: 1,
      name: "John Doe",
      email: "john@example.com",
      phone: "555-555-5555",
      address: "123 Main St",
    },
    {
      id: 2,
      name: "Jane Smith",
      email: "jane@example.com",
      phone: "555-555-5555",
      address: "456 Elm St",
    },
    {
      id: 3,
      name: "Bob Johnson",
      email: "bob@example.com",
      phone: "555-555-5555",
      address: "789 Oak St",
    },
    // Add more customer objects as needed
  ];

  return (
    <div className="customer-list grid grid-cols-1 gap-4 sm:grid-cols-2 md:grid-cols-3">
      {customers.map((customer) => (
        <Card key={customer.id}>
          <CustomerView customer={customer} />
          <hr className="my-2" />
          <div className="flex justify-end space-x-2">
            <Anchor to={`/customer/${customer.id}`} className="">
              Open
            </Anchor>
            <Anchor
              to={`/customer/delete/${customer.id}`}
              className="bg-red-400 hover:bg-red-500"
            >
              Delete
            </Anchor>
          </div>
        </Card>
      ))}
    </div>
  );
};

export default CustomersPage;
