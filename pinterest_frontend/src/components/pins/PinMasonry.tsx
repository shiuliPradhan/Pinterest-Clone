import React from "react";
import PinCard from "./PinCard";

interface Props {
  pins: any[];
}

const PinMasonry: React.FC<Props> = ({ pins }) => {
  return (
    <div className="masonry masonry-2 masonry-3 masonry-4 masonry-5">
      {pins.map((p) => (
        <div key={p.id} className="masonry-item">
          <PinCard pin={p} />
        </div>
      ))}
    </div>
  );
};

export default PinMasonry;
