

import React, { useState } from "react";
import Card from "../components/Card";
import Button from "../components/Button";
import { useCustomerStore } from "../useCustomerStore";
import { useParams } from "react-router-dom";

const DeleteConfirmationPage = () => {
  const [confirmed, setConfirmed] = useState(false);
  
  // get id from react router
  const {id} = useParams<{id: string}>();
  const iid = parseInt(id!);

  const handleConfirm = () => {
    // Perform delete operation here
    // ...

    // Set confirmed to true
    setConfirmed(true);
    useCustomerStore.getState().removeCustomer(iid);
  };

  const handleCancel = () => {
    // Redirect to previous page
    window.history.back();
  };

  return (
    <div className="w-[35rem] mx-auto">
      <h1>Delete Customer</h1>
      <Card>
        {confirmed ? (
          <p>Item successfully deleted.</p>
        ) : (
          <div>
            <h3>Confirmation</h3>
            <p>Are you sure you want to delete this item?</p>
            <div className="grid grid-cols-2 space-x-1">
              <Button className="bg-red-400 hover:bg-red-500" onClick={handleConfirm}>Yes, delete</Button>
              <Button onClick={handleCancel}>No</Button>
            </div>
          </div>
        )}
      </Card>
    </div>
  );
};

export default DeleteConfirmationPage;
