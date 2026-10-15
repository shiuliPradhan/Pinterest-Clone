import { useState } from "react";
import { userAPI } from "../../api/user.api";

const ForgotPasswordForm = () => {
  const [userIdOrEmail, setUserIdOrEmail] = useState("");
  const [status, setStatus] = useState<"idle" | "loading" | "success" | "error">("idle");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setStatus("loading");
    setMessage("");

    try {
      await userAPI.requestPasswordReset(userIdOrEmail);
      setStatus("success");
      setMessage("Password reset link/request sent!");
    } catch (err: any) {
      setStatus("error");
      setMessage(
        err?.response?.data?.errorMessage || "Failed to request password reset"
      );
    }
  };

  return (
    <form onSubmit={handleSubmit}>

      {/* Alerts */}
      {status === "success" && (
        <div className="alert alert-success text-center py-2">{message}</div>
      )}
      {status === "error" && (
        <div className="alert alert-danger text-center py-2">{message}</div>
      )}

      {/* Input */}
      <div className="mb-3">
        <label className="form-label">User ID or Email</label>
        <input
          type="text"
          className="form-control"
          placeholder="Enter user ID or email"
          value={userIdOrEmail}
          onChange={(e) => setUserIdOrEmail(e.target.value)}
        />
      </div>

      {/* Button */}
      <button
        className="btn btn-danger w-100 py-2"
        disabled={status === "loading"}
      >
        {status === "loading" ? "Sending..." : "Send Reset Request"}
      </button>

      {/* Back to login */}
      <div className="text-center mt-3">
        <a
          href="/auth/signin"
          className="text-danger fw-semibold text-decoration-none"
        >
          Back to login
        </a>
      </div>
    </form>
  );
};

export default ForgotPasswordForm;
