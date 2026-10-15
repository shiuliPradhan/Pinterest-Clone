import React from "react";

interface LoaderProps {
  size?: number;
  className?: string;
}

const Loader: React.FC<LoaderProps> = ({ size = 28, className = "" }) => {
  return (
    <div
      className={`spinner-border text-danger ${className}`}
      role="status"
      style={{
        width: size,
        height: size,
        borderWidth: "3px",
      }}
    >
      <span className="visually-hidden">Loading...</span>
    </div>
  );
};

export default Loader;
