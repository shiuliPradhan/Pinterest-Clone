import React from "react";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "outline" | "danger";
  loading?: boolean;
  className?: string;
}

const Button: React.FC<ButtonProps> = ({
  variant = "primary",
  loading = false,
  className = "",
  children,
  ...rest
}) => {
  const bootstrapClass = {
    primary: "btn btn-primary",
    secondary: "btn btn-secondary",
    outline: "btn btn-outline-secondary",
    danger: "btn btn-danger",
  }[variant];

  return (
    <button
      {...rest}
      className={`${bootstrapClass} ${className}`}
      disabled={loading || rest.disabled}
    >
      {loading ? "Loading..." : children}
    </button>
  );
};

export default Button;
