import React from "react";
import CustomerView from "../components/CustomerView";
import Card from "../components/Card";

interface CustomerPageProps {
  id: number;
}

const CustomerPage: React.FC<CustomerPageProps> = ({ id }) => {
  const customer = {
    id: id,
    name: "John Doe",
    email: "john.doe@example.com",
    phone: "123-456-7890",
    address: "123 Main St, City, State, Country",
  };

  return (
    <Card title="Customer Details">
      <CustomerView customer={customer} />
    </Card>
  );
};

export default CustomerPage;
