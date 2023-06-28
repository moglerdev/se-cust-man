import React from "react";

interface CardProps {
  title?: React.ReactNode;
  children: React.ReactNode;
}

const Card: React.FC<CardProps> = ({ title, children }) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-4">
      {title && <div className="mb-4">{title}</div>}
      <div>{children}</div>
    </div>
  );
};

export default Card;
