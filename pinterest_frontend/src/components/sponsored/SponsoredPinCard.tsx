import React, { useState } from "react";
import SponsorBadge from "./SponsorBadge";
import { sponsorsAPI } from "../../api/sponsors.api";
import Avatar from "../common/Avatar";

interface SponsoredAd {
  id: number;
  pinId: number;
  title: string;
  mediaUrl: string;
  businessName: string;
  clickRate: number;
}

interface Props {
  ad: SponsoredAd;
  className?: string;
}

const SponsoredPinCard: React.FC<Props> = ({ ad, className }) => {
  const [clickRate, setClickRate] = useState(ad.clickRate);

  const handleClick = async () => {
    try {
      const updated = await sponsorsAPI.updateClickRate(ad.id, clickRate + 1);
      setClickRate(
        updated.data?.clickRate ?? clickRate + 1
      );
    } catch (err) {
      console.error("Click-rate update failed:", err);
    }
  };

  return (
    <div
      className={`card shadow-sm border-0 rounded-4 overflow-hidden position-relative ${className || ""}`}
      style={{ cursor: "pointer", transition: "0.2s" }}
      onClick={handleClick}
      onMouseEnter={(e) => (e.currentTarget.style.boxShadow = "0 4px 12px rgba(0,0,0,0.15)")}
      onMouseLeave={(e) => (e.currentTarget.style.boxShadow = "")}
    >
      {/* Badge */}
      <div className="position-absolute top-0 start-0 m-2">
        <SponsorBadge />
      </div>

      {/* Image */}
      <img
        src={ad.mediaUrl}
        alt={ad.title}
        className="card-img-top"
        style={{ height: "260px", objectFit: "cover" }}
      />

      {/* Body */}
      <div className="card-body p-3">
        <h6 className="fw-semibold text-truncate" style={{ lineHeight: "1.2" }}>
          {ad.title}
        </h6>

        <div className="d-flex align-items-center mt-2 gap-2">
          <Avatar size={28} />
          <small className="text-muted">{ad.businessName}</small>
        </div>

        <small className="text-muted d-block mt-1">
          Engagement: {clickRate.toFixed(2)}%
        </small>
      </div>
    </div>
  );
};

export default SponsoredPinCard;
