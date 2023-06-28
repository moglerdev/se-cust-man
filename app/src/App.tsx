import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Sidebar from "./components/Sidebar";
import CustomersPage from "./pages/CustomersPage";
import CustomerPage from "./pages/CustomerPage";
import DeleteConfirmationPage from "./pages/DeleteConfirmationPage";

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <div className="flex h-screen">
        <Sidebar />
        <div className="w-full">
          <Routes>
            <Route path="/" element={<CustomersPage />} />
            <Route
              path="/customer/new"
              element={<CustomerPage id={-1} />}
            />
            <Route path="/customer/delete/:id" element={<DeleteConfirmationPage />} />
            <Route path="/customer/:id" element={<CustomerPage id={-1} />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
