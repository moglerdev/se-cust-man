import React, { createContext, useState } from "react";
import { Customer, Project, Task } from "./model";


interface ScmContextContent {
  customer: Customer | null;
  setCustomer: React.Dispatch<React.SetStateAction<Customer | null>>;
  project: Project | null;
  setProject: React.Dispatch<React.SetStateAction<Project | null>>;
  task: Task | null;
  setTask: React.Dispatch<React.SetStateAction<Task | null>>;
}

// Create the context
export const ScmContext = createContext<ScmContextContent | null>(null);

export const useScm = () => {
  const context = React.useContext(ScmContext);

  if (!context) {
    throw new Error("useScm must be used within a ScmProvider");
  }

  return context;
}


// Create a provider component
export const ScmProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [customer, setCustomer] = useState<Customer | null>(null);
  const [project, setProject] = useState<Project | null>(null);
  const [task, setTask] = useState<Task | null>(null);

  return (
    <ScmContext.Provider value={{
      customer,
      setCustomer,
      project,
      setProject,
      task,
      setTask,
    }}>
      {children}
    </ScmContext.Provider>
  );
};
