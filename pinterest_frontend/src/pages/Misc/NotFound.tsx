import React from "react";
import { Link } from "react-router-dom";

const NotFound: React.FC = () => {
  return (
    <div className="d-flex flex-column align-items-center justify-content-center text-center vh-100 px-3">
      <h1 className="fw-bold display-3 mb-3">404</h1>

      <p className="text-muted fs-5 mb-4">
        Oops! The page you're looking for doesn’t exist.
      </p>

      <Link to="/" className="btn btn-dark px-4 py-2">
        Go Home
      </Link>
    </div>
  );
};

export default NotFound;
