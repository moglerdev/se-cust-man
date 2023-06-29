import React from "react";
import { Customer } from "../model";

interface CustomerViewProps {
  customer: Customer;
}

const CustomerView: React.FC<CustomerViewProps> = ({ customer }) => {
  return (
    <div className="grid grid-cols-2 gap-4">
      <div>
        <p className="font-bold text-xl">Name:</p>
        <p className="text-lg">{customer.name}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Email:</p>
        <p className="text-lg">{customer.email}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Phone:</p>
        <p className="text-lg">{customer.phone}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Address:</p>
        <p className="text-lg">{customer.address}</p>
      </div>
    </div>
  );
};

export default CustomerView;
