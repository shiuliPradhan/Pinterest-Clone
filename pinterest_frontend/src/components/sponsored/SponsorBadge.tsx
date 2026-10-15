import React from "react";

interface Props {
  className?: string;
}

const SponsorBadge: React.FC<Props> = ({ className }) => {
  return (
    <span
      className={`badge rounded-pill bg-warning text-dark fw-semibold ${className || ""}`}
      style={{ fontSize: "0.7rem" }}
    >
      Sponsored
    </span>
  );
};

export default SponsorBadge;
