import React from "react";

const Footer: React.FC = () => {
  return (
    <footer className="bg-light border-top py-3 mt-auto">
      <div className="container text-center">
        <p className="text-muted mb-0">
          © {new Date().getFullYear()} Pinterest Clone — All Rights Reserved.
        </p>
      </div>
    </footer>
  );
};

export default Footer;
