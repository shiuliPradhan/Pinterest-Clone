import React from "react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  iconLeft?: React.ReactNode;
  iconRight?: React.ReactNode;
  className?: string;
}

const Input: React.FC<InputProps> = ({
  label,
  error,
  iconLeft,
  iconRight,
  className = "",
  ...rest
}) => {
  return (
    <div className={`w-100 ${className}`}>
      {label && (
        <label className="form-label fw-semibold small">
          {label}
        </label>
      )}

      <div className={`input-group ${error ? "is-invalid" : ""}`}>
        
        {iconLeft && (
          <span className="input-group-text bg-white border-end-0">
            {iconLeft}
          </span>
        )}

        <input
          {...rest}
          className={`form-control ${iconLeft ? "border-start-0" : ""} ${iconRight ? "border-end-0" : ""}`}
          style={{ paddingLeft: iconLeft ? 0 : undefined }}
        />

        {iconRight && (
          <span className="input-group-text bg-white border-start-0">
            {iconRight}
          </span>
        )}
      </div>

      {error && <div className="invalid-feedback d-block">{error}</div>}
    </div>
  );
};

export default Input;
