import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Sidebar from "./components/Sidebar";
import CustomersPage from "./pages/CustomersPage";
import CustomerPage, { CustomerNewPage } from "./pages/CustomerPage";
import DeleteConfirmationPage from "./pages/DeleteConfirmationPage";
import ProjectPage, { ProjectNewPage } from "./pages/ProjectPage";
import TaskPage, { TaskNewPage } from "./pages/TaskPage";

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
            <Route path="/project/:customer_id/:project_id" element={<ProjectPage />} />
            <Route path="/project/:customer_id/" element={<ProjectNewPage />} />
            <Route path="/project/:customer_id/:project_id/:task_id" element={<TaskPage />} />
            <Route path="/project/:customer_id/:project_id/new" element={<TaskNewPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
