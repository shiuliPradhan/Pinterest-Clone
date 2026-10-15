import { useState } from "react";
import { userAPI } from "../../api/user.api";

interface Props {
  token: string;
}

const ResetPasswordForm = ({ token }: Props) => {
  const [password, setPassword] = useState("");
  const [status, setStatus] = useState<"idle" | "loading" | "success" | "error">("idle");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!token) {
      setStatus("error");
      setMessage("Reset token missing or invalid.");
      return;
    }

    setStatus("loading");

    try {
      await userAPI.resetPassword(token, password);
      setStatus("success");
      setMessage("Password updated successfully!");
    } catch (err: any) {
      setStatus("error");
      setMessage(err?.response?.data?.errorMessage || "Reset failed");
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

      {/* New Password */}
      <div className="mb-3">
        <label className="form-label">New Password</label>
        <input
          type="password"
          className="form-control"
          placeholder="Enter new password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      <button className="btn btn-danger w-100 py-2" disabled={status === "loading"}>
        {status === "loading" ? "Updating..." : "Reset Password"}
      </button>
    </form>
  );
};

export default ResetPasswordForm;
