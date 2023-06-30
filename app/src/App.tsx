import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Sidebar from "./components/Sidebar";
import CustomersPage from "./pages/CustomersPage";
import CustomerPage, { CustomerNewPage } from "./pages/CustomerPage";
import DeleteConfirmationPage from "./pages/DeleteConfirmationPage";

const App: React.FC = () => {

  return (
    <BrowserRouter>
      <div className="flex h-screen">
        <Sidebar />
        <div className="w-full p-10">
          <Routes>
            <Route path="/" element={<CustomersPage />} />
            <Route
              path="/customer/new"
              element={<CustomerNewPage  />}
            />
            <Route path="/customer/delete/:id" element={<DeleteConfirmationPage />} />
            <Route path="/customer/:id" element={<CustomerPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
