import React from "react";
import { BusinessProfile } from "../../types/user";

interface Props {
  business: any;
}

const BusinessProfileHeader: React.FC<Props> = ({ business }) => {
  return (
    <div className="d-flex align-items-center gap-3">
      <img
        src={business.logoUrl || "https://via.placeholder.com/80"}
        alt={business.name}
        className="rounded-circle"
        style={{
          width: "70px",
          height: "70px",
          objectFit: "cover",
        }}
      />

      <div>
        <h3 className="fw-bold mb-1">{business.name}</h3>
        <p className="text-muted mb-0">{business.category}</p>
      </div>
    </div>
  );
};

export default BusinessProfileHeader;
