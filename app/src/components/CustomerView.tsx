import React from "react";
import { Customer } from "../model";
import Input from "./Input";
import Button from "./Button";

interface CustomerEditViewProps {
  customer: Customer;
  onSave: (customer: Customer) => void;
}

export const CustomerEditView: React.FC<CustomerEditViewProps> = ({ customer, onSave}) => {
  const [name, setName] = React.useState(customer.name);
  const [email, setEmail] = React.useState(customer.email);
  const [phone, setPhone] = React.useState(customer.phone);
  const [address, setAddress] = React.useState(customer.address);

  React.useEffect(() => {
    setName(customer.name);
    setEmail(customer.email);
    setPhone(customer.phone);
    setAddress(customer.address);
  }
  , [customer]);

  const handleNameChange = (value: string) => {
    setName(value);
  };

  const handleEmailChange = (value: string) => {
    setEmail(value);
  };

  const handlePhoneChange = (value: string) => {
    setPhone(value);
  };

  const handleAddressChange = (value: string) => {
    setAddress(value);
  };

  const handleSave = () => {
    onSave({
      ...customer,
      name,
      email,
      phone,
      address,
    });
  };

  return (
    <div className="grid grid-cols-2 gap-4">
      <div>
        <p className="font-bold text-xl">Name:</p>
        <Input value={name} onChange={handleNameChange} />
      </div>
      <div>
        <p className="font-bold text-xl">Email:</p>
        <Input value={email} onChange={handleEmailChange} />
      </div>
      <div>
        <p className="font-bold text-xl">Phone:</p>
        <Input value={phone} onChange={handlePhoneChange} />
      </div>
      <div>
        <p className="font-bold text-xl">Address:</p>
        <Input value={address} onChange={handleAddressChange} />
      </div>
      <div>
        <Button onClick={handleSave} className="bg-blue-500 hover:bg-blue-600">Save</Button>
      </div>
    </div>
  );
};

const CustomerView: React.FC<{ customer: Customer }> = ({ customer}) => {
  return (
    <div className="grid grid-cols-2 gap-4">
      <div>
        <p className="font-bold text-xl">Name:</p>
        <p>{customer.name}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Email:</p>
        <p>{customer.email}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Phone:</p>
        <p>{customer.phone}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Address:</p>
        <p>{customer.address}</p>
      </div>
    </div>
  );
};

export default CustomerView;
