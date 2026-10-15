import React from "react";
import { Link, NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import SearchBar from "../search/SearchBar";
import Avatar from "../common/Avatar";

const NavBar: React.FC = () => {
  const { user, logout } = useAuth();

  const keyword = (kw: string) => {
    console.log("Search for:", kw);
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top">
      <div className="container-fluid px-3">

        {/* Logo */}
        <Link to="/" className="navbar-brand fw-bold text-danger fs-3">
          Pinterest
        </Link>

        {/* Toggler for mobile */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#mainNavbar"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        {/* Menu */}
        <div className="collapse navbar-collapse" id="mainNavbar">

          {/* Search Bar (center) */}
          <div className="mx-auto d-none d-md-block" style={{ width: "40%" }}>
            <SearchBar onSearch={keyword} />
          </div>

          {/* Right Side */}
          <ul className="navbar-nav ms-auto align-items-center gap-3">

            {user ? (
              <>
                <li className="nav-item">
                  <NavLink to="/pin/create" className="nav-link">
                    Create
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink to="/notifications" className="nav-link">
                    Notifications
                  </NavLink>
                </li>

                <li className="nav-item">
                  <Avatar size={36} />
                </li>

                <li className="nav-item">
                  <button className="btn btn-outline-secondary btn-sm" onClick={logout}>
                    Logout
                  </button>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item">
                  <NavLink to="/auth/signin" className="btn btn-outline-secondary btn-sm">
                    Login
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink to="/auth/signup" className="btn btn-danger btn-sm">
                    Sign Up
                  </NavLink>
                </li>
              </>
            )}

          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
