import { useState } from "react";
import { userAPI } from "../../api/user.api";
import { useNavigate } from "react-router-dom";

const SignUpForm = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
  });

  const [confirmPassword, setConfirmPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState<"idle" | "success" | "error">("idle");
  const [message, setMessage] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setStatus("idle");
    setMessage("");

    try {
      if (form.password !== confirmPassword) {
        setStatus("error");
        setMessage("Passwords do not match");
        setLoading(false);
        return;
      }
      await userAPI.register(form);
      setStatus("success");
      setMessage("Registration successful! Please sign in.");
      setTimeout(() => {
        navigate("/auth/signin");
      },5000);

    } catch (err: any) {
      setStatus("error");
      setMessage(err?.response?.data?.errorMessage || "Registration failed");
    }
    setLoading(false);
  };

  return (
    <form onSubmit={handleSubmit}>

      {/* Bootstrap Alerts */}
      {status === "success" && (
        <div className="alert alert-success text-center py-2">
          {message}
        </div>
      )}

      {status === "error" && (
        <div className="alert alert-danger text-center py-2">
          {message}
        </div>
      )}

      {/* Username */}
      <div className="mb-3">
        <label className="form-label">Username</label>
        <input
          type="text"
          className="form-control"
          name="username"
          value={form.username}
          onChange={handleChange}
          placeholder="Enter username"
        />
      </div>

      {/* Email */}
      <div className="mb-3">
        <label className="form-label">Email</label>
        <input
          type="email"
          className="form-control"
          name="email"
          value={form.email}
          onChange={handleChange}
          placeholder="Enter email"
        />
      </div>

      {/* Password */}
      <div className="mb-3">
        <label className="form-label">Password</label>
        <input
          type="password"
          className="form-control"
          name="password"
          value={form.password}
          onChange={handleChange}
          placeholder="Enter password"
        />
      </div>

      <div className="mb-3">
        <label className="form-label">Password</label>
        <input
          type="password"
          className="form-control"
          name="confirmPassword"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          placeholder="Confirm password"
        />
      </div>

      {/* Submit Button */}
      <button
        type="submit"
        className="btn btn-danger w-100 py-2"
        disabled={loading}
      >
        {loading ? "Creating account..." : "Sign Up"}
      </button>

      {/* Sign In Link */}
      <div className="text-center mt-3">
        <span className="text-muted">Already have an account? </span>
        <a
          href="/auth/signin"
          className="text-danger fw-semibold text-decoration-none"
        >
          Sign in
        </a>
      </div>
    </form>
  );
};

export default SignUpForm;
