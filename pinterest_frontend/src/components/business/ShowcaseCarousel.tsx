import React from "react";
import { Showcase } from "../../types/user";

interface Props {
  showcases: Showcase[];
}

const ShowcaseCarousel: React.FC<Props> = ({ showcases }) => {
  if (!showcases || showcases.length === 0) {
    return (
      <div className="text-center text-muted py-3">
        No showcases available.
      </div>
    );
  }

  return (
    <div
      className="d-flex overflow-auto gap-3 py-3"
      style={{ whiteSpace: "nowrap" }}
    >
      {showcases.map((showcase) => (
        <div
          key={showcase.id}
          className="card shadow-sm border-0"
          style={{
            minWidth: "260px",
            borderRadius: "12px",
            cursor: "pointer",
          }}
        >
          <img
            src={showcase.coverImageUrl || "https://via.placeholder.com/400"}
            alt={showcase.title}
            className="card-img-top"
            style={{
              height: "160px",
              objectFit: "cover",
              borderTopLeftRadius: "12px",
              borderTopRightRadius: "12px",
            }}
          />

          <div className="card-body">
            <h5 className="card-title fw-bold mb-1">{showcase.title}</h5>

            <p className="card-text text-muted small mb-0" style={{ minHeight: "32px" }}>
              {showcase.description}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ShowcaseCarousel;
