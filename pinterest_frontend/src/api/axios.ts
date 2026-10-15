import axios from "axios";

// Backend base URL (edit if needed)
const API_BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Automatically attach JWT token for every request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (
    config.url?.includes("/auth/login") ||
    config.url?.includes("/users/register")
  ) {
    return config;
  }

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});


// Global error interceptor
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Automatically logout if token is expired (matches backend exception)
    if (
      error.response &&
      error.response.data &&
      error.response.data.errorMessage === "Service.TOKEN_EXPIRED"
    ) {
      localStorage.removeItem("token");
      window.location.href = "/signin";
    }

    // For debugging
    console.error("API Error:", error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export default api;
