import React from "react";
import ForgotPasswordForm from "../../components/auth/ForgotPasswordForm";

const ForgotPasswordPage: React.FC = () => {
  return (
    <div className="d-flex align-items-center justify-content-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "100%", maxWidth: "420px" }}>
        
        {/* Centered Logo */}
        <div className="d-flex justify-content-center mb-3">
          <img src="/assets/images/pinterest-logo-png-2009.png" alt="Pinterest Logo" width={40} />
        </div>

        <h2 className="text-center fw-bold mb-3">Forgot Password</h2>
        <p className="text-center text-muted mb-4">
          Enter your email or username to receive a reset link
        </p>

        <ForgotPasswordForm />
      </div>
    </div>
  );
};

export default ForgotPasswordPage;
