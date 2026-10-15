import React, { useState } from "react";

interface Props {
  onSubmit: (text: string) => Promise<void>;
  loading?: boolean;
}

const CommentInput: React.FC<Props> = ({ onSubmit, loading = false }) => {
  const [text, setText] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!text.trim()) return;

    await onSubmit(text.trim());
    setText("");
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="d-flex align-items-center gap-2 p-3 bg-white border rounded shadow-sm"
    >
      <input
        type="text"
        className="form-control"
        placeholder="Add a comment..."
        value={text}
        onChange={(e) => setText(e.target.value)}
      />

      <button
        type="submit"
        className="btn btn-danger"
        disabled={!text.trim() || loading}
      >
        {loading ? "Posting..." : "Post"}
      </button>
    </form>
  );
};

export default CommentInput;
