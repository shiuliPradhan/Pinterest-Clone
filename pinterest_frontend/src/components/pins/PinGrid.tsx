import React from "react";
import PinCard from "./PinCard";

interface Props {
  pins: any[];
}

const PinGrid: React.FC<Props> = ({ pins }) => {
  return (
    <div className="row g-3">
      {pins.map((p) => (
        <div key={p.id} className="col-6 col-sm-4 col-md-3 col-lg-3">
          <PinCard pin={p} />
        </div>
      ))}
    </div>
  );
};

export default PinGrid;
