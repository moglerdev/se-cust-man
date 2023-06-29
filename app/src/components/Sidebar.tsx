import React from "react";
import { Link } from "react-router-dom";

interface SidebarItemProps {
  to: string;
  children: React.ReactNode;
}

const SidebarItem: React.FC<SidebarItemProps> = ({ to, children }) => {
  return (
    <li className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold rounded">
      <Link className="block py-2 px-4" to={to}>
        {children}
      </Link>
    </li>
  );
};

const Sidebar: React.FC = () => {
  return (
    <div className="sidebar bg-gray-200 p-4 w-96">
      <h2 className="text-lg font-semibold mb-4">Customer Actions</h2>
      <ul className="space-y-2 w-full">
        <SidebarItem to="/">Customer List</SidebarItem>
        <SidebarItem to="/customer/new">Add Customer</SidebarItem>
      </ul>
    </div>
  );
};

export default Sidebar;
