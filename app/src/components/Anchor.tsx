import React from "react";
import { Link } from "react-router-dom";

interface AnchorProps {
  to: string;
  className?: string;
  children: React.ReactNode;
}

const Anchor: React.FC<AnchorProps> = ({ to, className, children }) => {
  const buttonClasses = `bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded w-full ${className}`;

  return (
    <Link className={buttonClasses} to={to}>
      {children}
    </Link>
  );
};

export default Anchor;
