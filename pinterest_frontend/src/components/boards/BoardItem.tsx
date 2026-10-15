import React from "react";
import { Board } from "../../types/board";
import { Link } from "react-router-dom";

interface Props {
  board: Board;
}

const BoardItem: React.FC<Props> = ({ board }) => {
  return (
    <Link
      to={`/boards/${board.id}`}
      className="text-decoration-none"
      style={{ color: "inherit" }}
    >
      <div
        className="card shadow-sm border-0 h-100"
        style={{ cursor: "pointer", borderRadius: "12px" }}
      >
        <img
          src={board.coverImageUrl || "https://via.placeholder.com/300"}
          alt={board.title}
          className="card-img-top"
          style={{
            height: "180px",
            objectFit: "cover",
            borderTopLeftRadius: "12px",
            borderTopRightRadius: "12px",
          }}
        />

        <div className="card-body">

          <h5 className="card-title fw-bold mb-1">{board.title}</h5>

          <p className="card-text text-muted small mb-2" style={{ minHeight: "32px" }}>
            {board.description || "No description"}
          </p>

          <p className="text-muted small">
            {board.isPrivate ? "🔒 Private Board" : "🌍 Public Board"}
          </p>

        </div>
      </div>
    </Link>
  );
};

export default BoardItem;
