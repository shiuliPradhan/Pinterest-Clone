import { useState } from "react";
import { loginUser } from "../../api/auth.api";
import { useAuth } from "../../hooks/useAuth";

const SignInForm = () => {
  const { setToken, setUser } = useAuth();

  const [form, setForm] = useState({
    usernameOrEmail: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setErrorMsg("");

    try {
      const res = await loginUser(form);
      setToken(res.trim());
      localStorage.setItem("token", res.trim());
    } catch (err: any) {
      setErrorMsg(err?.response?.data?.errorMessage || "Invalid credentials");
    }


    setLoading(false);
  };

  return (
    <form onSubmit={handleSubmit}>
      {errorMsg && <div className="alert alert-danger text-center">{errorMsg}</div>}

      <div className="mb-3">
        <label className="form-label">Username or Email</label>
        <input
          type="text"
          className="form-control"
          name="usernameOrEmail"
          value={form.usernameOrEmail}
          onChange={handleChange}
          placeholder="Enter your email"
        />
      </div>

      <div className="mb-3">
        <label className="form-label">Password</label>
        <input
          type="password"
          className="form-control"
          name="password"
          value={form.password}
          onChange={handleChange}
          placeholder="Enter your password"
        />
      </div>

      <button
        type="submit"
        className="btn btn-danger w-100 py-2"
        disabled={loading}
      >
        {loading ? "Signing in..." : "Sign In"}
      </button>
      <div className="text-center mt-3">
        <span className="text-muted">Don't have an account? </span>
        <a href="/auth/signup" className="text-danger fw-semibold text-decoration-none">
          Create one
        </a>
      </div>

      <div className="text-center mt-3">
        <a href="/auth/forgot-password" className="text-danger fw-semibold text-decoration-none">
          Forgot Password?
        </a>
      </div>
    </form>
  );
};

export default SignInForm;
