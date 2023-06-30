import React from "react";
import { Link } from "react-router-dom";
import Button from "./Button";
import { useCustomerStore } from "../useCustomerStore";

interface SidebarLinkProps {
  to: string;
  children: React.ReactNode;
}interface SidebarButtonProps {
  onClick: () => void;
  children: React.ReactNode;
}

const SidebarItem: React.FC<React.PropsWithChildren> = ({ children }) => {
  return (
    <li className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold rounded">
      {children}
    </li>
  );
};
const SidebarLink: React.FC<SidebarLinkProps> = ({ to, children }) => {
  return (
    <SidebarItem>
      <Link className="block py-2 px-4 text-center" to={to}>
        {children}
      </Link>
    </SidebarItem>
  );
};
const SidebarButton: React.FC<SidebarButtonProps> = ({ onClick, children }) => {
  return (
    <SidebarItem>
      <Button className="block py-2 px-4" onClick={onClick}>
        {children}
      </Button>
    </SidebarItem>
  );
};

const Sidebar: React.FC = () => {

  const handleUndo = () => {
    // Perform undo operation here
    // ...
    useCustomerStore.getState().undo();
  };

  const handleRedo = () => {
    // Perform undo operation here
    // ...
    useCustomerStore.getState().redo();
  };

  return (
    <div className="sidebar bg-gray-200 p-4 w-96">
      <h2 className="text-lg font-semibold mb-4">Customer Actions</h2>
      <ul className="space-y-2 w-full">
        <SidebarLink to="/">Customer List</SidebarLink>
        <SidebarLink to="/customer/new">Add Customer</SidebarLink>
        <SidebarButton onClick={handleUndo}>Undo</SidebarButton>
        <SidebarButton onClick={handleRedo}>Redo</SidebarButton>
      </ul>
    </div>
  );
};

export default Sidebar;
