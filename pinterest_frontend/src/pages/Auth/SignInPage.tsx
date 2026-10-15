import React from "react";
import SignInForm from "../../components/auth/SignInForm";
import { useAuth } from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const SignInPage: React.FC = () => {
  const { token } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      navigate("/"); // 🔥 redirect once logged in
    }
  }, [token]);
  return (
    <div className="d-flex align-items-center justify-content-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "100%", maxWidth: "420px" }}>
        <div className="d-flex justify-content-center mb-3">
          <img src="/assets/images/pinterest-logo-png-2009.png" alt="Pinterest Logo" width={40} />
        </div>

        <h2 className="text-center fw-bold mb-3">Welcome Back</h2>
        <p className="text-center text-muted mb-4">Sign in to continue</p>

        <SignInForm />
      </div>
    </div>
  );
};

export default SignInPage;
