

import React, { useState } from "react";
import Card from "../components/Card";

const DeleteConfirmationPage = () => {
  const [confirmed, setConfirmed] = useState(false);

  const handleConfirm = () => {
    // Perform delete operation here
    // ...

    // Set confirmed to true
    setConfirmed(true);
  };

  return (
    <Card>
      {confirmed ? (
        <p>Item successfully deleted.</p>
      ) : (
        <div>
          <h3>Confirmation</h3>
          <p>Are you sure you want to delete this item?</p>
          <button onClick={handleConfirm}>Yes, delete</button>
        </div>
      )}
    </Card>
  );
};

export default DeleteConfirmationPage;
