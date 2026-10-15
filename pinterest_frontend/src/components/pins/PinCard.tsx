import React from "react";
import SaveButton from "./SaveButton";
import { Link } from "react-router-dom";

interface Props {
  pin: any;
  onClick?: () => void;
}

const PinCard: React.FC<Props> = ({ pin }) => {
  return (
    <div
      className="position-relative mb-3"
      style={{ breakInside: "avoid-column", cursor: "pointer" }}
    >
      <Link to={`/pin/${pin.id}`}>
        <img
          src={pin.sourceUrl}
          alt={pin.title}
          className="img-fluid rounded-4"
          style={{
            objectFit: "cover",
            width: "100%",
          }}
        />
      </Link>

      {/* Hover Save Button */}
      <div
        className="position-absolute top-0 end-0 p-2"
        style={{
          opacity: 0,
          transition: "opacity 0.25s ease",
        }}
        onMouseEnter={(e) => (e.currentTarget.style.opacity = "1")}
        onMouseLeave={(e) => (e.currentTarget.style.opacity = "0")}
      >
        <SaveButton pinId={pin.id} />
      </div>

      {/* Title */}
      <div className="pt-2">
        <h6 className="fw-semibold text-truncate">{pin.title}</h6>
      </div>
    </div>
  );
};

export default PinCard;
