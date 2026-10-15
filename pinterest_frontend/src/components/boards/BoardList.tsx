import React, { useEffect, useState } from "react";
import { boardAPI } from "../../api/board.api";
import BoardItem from "./BoardItem";
import { Board } from "../../types/board";
import Loader from "../common/Loader";

interface Props {
  userId: number;
}

const BoardList: React.FC<Props> = ({ userId }) => {
  const [boards, setBoards] = useState<Board[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBoards = async () => {
      try {
        const data = await boardAPI.getBoardsByUser(userId);
        setBoards(data.data);
      } catch (err) {
        console.error("Error fetching boards:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchBoards();
  }, [userId]);

  if (loading) return <Loader />;

  if (boards.length === 0)
    return (
      <div className="text-center text-muted py-4">
        No boards found.
      </div>
    );

  return (
    <div className="container py-3">
      <div className="row g-4">
        {boards.map((board) => (
          <div key={board.id} className="col-6 col-sm-4 col-md-3">
            <BoardItem board={board} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default BoardList;
