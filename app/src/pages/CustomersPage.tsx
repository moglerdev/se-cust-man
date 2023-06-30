import React from "react";
import CustomerView from "../components/CustomerView";
import { Customer } from "../model";
import Card from "../components/Card";
import Anchor from "../components/Anchor";
import { useCustomerStore } from "../store";


const CustomerList = ({customers}: {customers: Customer[]}) => {

  if (!customers.length) {
    return <p>No customers found.</p>;
  }

  return <>{customers.map((customer) => (
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
  ))}</>
}

const CustomersPage: React.FC = () => {
  // Example customer data
  const customers = useCustomerStore((state) => state.customers);

  return (
    <div>
      <h1>Customers</h1>
      <div className="customer-list grid grid-cols-1 gap-4 sm:grid-cols-2 md:grid-cols-3">
        <CustomerList customers={customers} />
      </div>
    </div>
  );
};

export default CustomersPage;
