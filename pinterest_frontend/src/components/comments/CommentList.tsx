import React from "react";
import Avatar from "../common/Avatar";
import { formatDistanceToNow } from "date-fns";

export interface CommentItem {
  id: number;
  user: {
    id: number;
    username: string;
    profileImageUrl?: string | null;
  };
  text: string;
  createdAt: string;
}

interface Props {
  comments: CommentItem[];
  loading?: boolean;
}

const CommentList: React.FC<Props> = ({ comments, loading = false }) => {
  if (loading) {
    return (
      <div className="p-3 text-muted small">
        Loading comments…
      </div>
    );
  }

  if (comments.length === 0) {
    return (
      <div className="p-3 text-muted small">
        No comments yet. Be the first to comment!
      </div>
    );
  }

  return (
    <div className="d-flex flex-column gap-3">
      {comments.map((c) => (
        <div key={c.id} className="d-flex gap-3 pb-3 border-bottom">

          {/* Avatar */}
          <Avatar
            src={c.user.profileImageUrl || undefined}
            alt={c.user.username}
            size={40}
          />

          {/* Comment Text */}
          <div className="flex-grow-1">
            <div className="d-flex justify-content-between align-items-center">
              <span className="fw-semibold small">
                {c.user.username}
              </span>

              <span className="text-muted small">
                {formatDistanceToNow(new Date(c.createdAt), {
                  addSuffix: true,
                })}
              </span>
            </div>

            <p className="small mt-1 mb-0">
              {c.text}
            </p>
          </div>

        </div>
      ))}
    </div>
  );
};

export default CommentList;
