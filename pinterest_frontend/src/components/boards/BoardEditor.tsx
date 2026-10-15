import { useState, useEffect } from "react";
import { boardAPI } from "../../api/board.api";

interface BoardEditorProps {
  boardId?: number; // If present → edit mode
  userId: number;
  onSuccess?: () => void;
}

export default function BoardEditor({ boardId, userId, onSuccess }: BoardEditorProps) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [isPrivate, setIsPrivate] = useState(false);
  const [coverImageUrl, setCoverImageUrl] = useState("");
  const [loading, setLoading] = useState(false);

  // Fetch board in edit mode
  useEffect(() => {
    if (boardId) {
      (async () => {
        try {
          const res = await boardAPI.getBoard(boardId);
          const b = res.data;
          setTitle(b.title);
          setDescription(b.description || "");
          setIsPrivate(b.isPrivate || false);
          setCoverImageUrl(b.coverImageUrl || "");
        } catch (err) {
          console.error("Failed to load board", err);
        }
      })();
    }
  }, [boardId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const payload = {
        userId,
        title,
        description,
        isPrivate,
        coverImageUrl,
      };

      if (boardId) {
        await boardAPI.updateBoard(boardId, payload);
      } else {
        await boardAPI.createBoard(payload);
      }

      if (onSuccess) onSuccess();
    } catch (err) {
      console.error("Failed to save board", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="container p-4 bg-white shadow rounded"
      style={{ maxWidth: "600px" }}
    >
      <h3 className="fw-bold mb-3">
        {boardId ? "Edit Board" : "Create Board"}
      </h3>

      {/* Title */}
      <div className="mb-3">
        <label className="form-label">Title</label>
        <input
          type="text"
          className="form-control"
          placeholder="Board title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>

      {/* Description */}
      <div className="mb-3">
        <label className="form-label">Description</label>
        <textarea
          className="form-control"
          placeholder="Optional description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows={3}
        ></textarea>
      </div>

      {/* Cover Image URL */}
      <div className="mb-3">
        <label className="form-label">Cover Image URL</label>
        <input
          type="url"
          className="form-control"
          placeholder="https://example.com/cover.jpg"
          value={coverImageUrl}
          onChange={(e) => setCoverImageUrl(e.target.value)}
        />
      </div>

      {/* Private Toggle */}
      <div className="form-check mb-3">
        <input
          className="form-check-input"
          type="checkbox"
          checked={isPrivate}
          id="privateBoard"
          onChange={(e) => setIsPrivate(e.target.checked)}
        />
        <label className="form-check-label" htmlFor="privateBoard">
          Private Board
        </label>
      </div>

      {/* Submit Button */}
      <button className="btn btn-danger w-100" disabled={loading}>
        {loading
          ? "Saving..."
          : boardId
          ? "Update Board"
          : "Create Board"}
      </button>
    </form>
  );
}
