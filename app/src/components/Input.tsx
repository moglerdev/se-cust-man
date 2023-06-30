import React from 'react';

interface InputProps {
  label?: string;
  value: string;
  onChange: (value: string) => void;
}

const Input: React.FC<InputProps> = ({ label, value, onChange }) => {
  return (
    <div className="flex flex-col">
      <label className="text-gray-700 font-bold mb-2">{label}</label>
      <input
        className="border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        type="text"
        value={value}
        onChange={(event) => onChange(event.target.value)}
      />
    </div>
  );
};

export default Input;