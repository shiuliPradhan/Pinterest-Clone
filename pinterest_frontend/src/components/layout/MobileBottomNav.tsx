import React from "react";
import { NavLink } from "react-router-dom";
import { Home, PlusSquare, User, Search } from "lucide-react";

const MobileBottomNav: React.FC = () => {
  return (
    <nav 
      className="d-md-none bg-white shadow position-fixed bottom-0 start-0 end-0 z-3"
      style={{ borderTop: "1px solid #eee" }}
    >
      <div className="d-flex justify-content-around py-2">

        <NavLink 
          to="/" 
          className="text-secondary text-decoration-none d-flex flex-column align-items-center"
        >
          <Home size={22} />
        </NavLink>

        <NavLink 
          to="/explore" 
          className="text-secondary text-decoration-none d-flex flex-column align-items-center"
        >
          <Search size={22} />
        </NavLink>

        <NavLink 
          to="/pin/create" 
          className="text-secondary text-decoration-none d-flex flex-column align-items-center"
        >
          <PlusSquare size={22} />
        </NavLink>

        <NavLink 
          to="/profile" 
          className="text-secondary text-decoration-none d-flex flex-column align-items-center"
        >
          <User size={22} />
        </NavLink>

      </div>
    </nav>
  );
};

export default MobileBottomNav;
