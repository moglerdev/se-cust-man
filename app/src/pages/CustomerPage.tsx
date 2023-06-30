import React from "react";
import CustomerView, { CustomerEditView } from "../components/CustomerView";
import Card from "../components/Card";

import { useCustomerStore } from "../store";
import { Customer } from "../model";
import { Navigate, useNavigate, useParams } from "react-router-dom";

const CustomerPage: React.FC = () => {

  // load customer from useCustomer hook
  // get id from react router
  const {id} = useParams<{id: string}>();
  console.log("CustomerPage", id);
  const iid = Number.parseInt(id!);

  const customer = useCustomerStore((state) => state.customers.find((c) => c.id === iid));

  const handleSave = (customer: Customer) => {
    useCustomerStore.getState().setCustomer(customer);
  };

  if (!customer) {
    return <Navigate to="/" />;
  }

  return (
    <Card title="Customer Details">
      <CustomerEditView onSave={handleSave} customer={customer} />
      <hr className="my-10"/>
      <h2>Projects</h2>
    </Card>
  );
};

export const CustomerNewPage: React.FC = () => {
  // new customer
  const customer = {
    id: -1,
    name: "",
    email: "",
    phone: "",
    address: "",
  };

  // react router hook
  const nav = useNavigate();

  const handleSave = (customer: Customer) => {
    const id = useCustomerStore.getState().addCustomer(customer);
    nav(`/customer/${id}`);
  };

  return (
    <Card title="Customer Details">
      <CustomerEditView onSave={handleSave} customer={customer} />
      <hr className="my-10"/>
      <h2>Projects</h2>
    </Card>
  );
};

export default CustomerPage;
