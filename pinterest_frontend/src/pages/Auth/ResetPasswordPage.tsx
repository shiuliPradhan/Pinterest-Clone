import React from "react";
import { useParams } from "react-router-dom";
import ResetPasswordForm from "../../components/auth/ResetPasswordForm";

const ResetPasswordPage: React.FC = () => {
  const { token } = useParams();

  return (
    <div className="d-flex align-items-center justify-content-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "100%", maxWidth: "420px" }}>
        
        {/* Centered Logo */}
        <div className="d-flex justify-content-center mb-3">
          <img src="/assets/images/pinterest-logo-png-2009.png" alt="Pinterest Logo" width={40} />
        </div>

        <h2 className="text-center fw-bold mb-3">Reset Password</h2>
        <p className="text-center text-muted mb-4">
          Enter your new password below
        </p>

        <ResetPasswordForm token={token || ""} />
      </div>
    </div>
  );
};

export default ResetPasswordPage;
