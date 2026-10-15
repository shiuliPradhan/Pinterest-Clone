import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { boardAPI } from "../../api/board.api";
import { pinAPI } from "../../api/pin.api";
import Loader from "../../components/common/Loader";
import PinMasonry from "../../components/pins/PinMasonry";

const BoardPage: React.FC = () => {
  const { id } = useParams();
  const boardId = Number(id);

  const [board, setBoard] = useState<any>(null);
  const [pins, setPins] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!boardId) return;

    const fetchData = async () => {
      try {
        setLoading(true);

        // FIXED: CORRECT API
        const boardRes = await boardAPI.getBoard(boardId);
        const pinRes = await pinAPI.getPinsByBoard(boardId);

        setBoard(boardRes.data);
        setPins(pinRes.data);
      } catch (err: any) {
        setError(err.message || "Failed to load board");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [boardId]);

  if (loading) return <Loader />;
  if (error)
    return (
      <div className="alert alert-danger text-center my-4">{error}</div>
    );

  return (
    <div className="container py-4">
      <div className="mx-auto" style={{ maxWidth: "820px" }}>
        {/* Board Header */}
        <div className="text-center mb-4">

          {board?.coverImageUrl && (
            <img
              src={board.coverImageUrl}
              alt={board.title}
              className="img-fluid rounded mb-3"
              style={{ maxHeight: "300px", objectFit: "cover", width: "100%" }}
            />
          )}

          <h1 className="fw-bold">{board?.title}</h1>

          {board?.description && (
            <p className="text-muted mt-2">{board.description}</p>
          )}
        </div>

        {/* Masonry Grid */}
        <PinMasonry pins={pins} />
      </div>
    </div>
  );
};

export default BoardPage;
