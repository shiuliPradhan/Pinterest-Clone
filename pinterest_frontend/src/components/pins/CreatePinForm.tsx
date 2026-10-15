import React, { useState, useEffect } from "react";
import { pinAPI } from "../../api/pin.api";
import { useAuth } from "../../hooks/useAuth";
import Input from "../common/Input";
import Button from "../common/Button";
import { boardAPI } from "../../api/board.api";

interface Props {
  onSubmit: (formData: any, isDraft: boolean) => void;
}

const CreatePinForm: React.FC<Props> = ({ onSubmit }) => {
  const { user } = useAuth();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [boardId, setBoardId] = useState<number | null>(null);
  const [boards, setBoards] = useState<any[]>([]);
  const [isPrivate, setIsPrivate] = useState(false);

  useEffect(() => {
    if (user) {
      boardAPI.getBoardsByUser(user.id).then((res) => setBoards(res.data));
    }
  }, [user]);

  const handleSubmit = async (status: "DRAFT" | "PUBLISHED") => {
    if (!user) return;

    const payload = {
      title,
      description,
      sourceUrl: imageUrl,
      boardId: boardId || 0,
      userId: user.id,
      isPrivate,
      status,
    };

    await pinAPI.createPin(payload);

    if (onSubmit) onSubmit(payload, status === "DRAFT");
  };

  return (
    <div
      className="bg-white shadow rounded-4 p-4 mx-auto"
      style={{ maxWidth: "600px" }}
    >
      <h2 className="fw-bold fs-4 mb-4">Create a Pin</h2>

      {/* Title */}
      <Input
        label="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="mb-3"
      />

      {/* Description */}
      <label className="form-label fw-semibold small">Description</label>
      <textarea
        className="form-control mb-3"
        rows={3}
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      ></textarea>

      {/* Image URL */}
      <Input
        label="Image URL"
        value={imageUrl}
        onChange={(e) => setImageUrl(e.target.value)}
        className="mb-3"
      />

      {/* Board */}
      <label className="form-label fw-semibold small">Board</label>
      <select
        className="form-select mb-3"
        onChange={(e) => setBoardId(Number(e.target.value))}
      >
        <option>Select board</option>
        {boards.map((b) => (
          <option key={b.id} value={b.id}>
            {b.title}
          </option>
        ))}
      </select>

      {/* Private checkbox */}
      <div className="form-check mb-4">
        <input
          className="form-check-input"
          type="checkbox"
          checked={isPrivate}
          id="privatePin"
          onChange={() => setIsPrivate(!isPrivate)}
        />
        <label className="form-check-label" htmlFor="privatePin">
          Private Pin
        </label>
      </div>

      {/* Buttons */}
      <div className="d-flex justify-content-between">
        <Button variant="secondary" onClick={() => handleSubmit("DRAFT")}>
          Save as Draft
        </Button>

        <Button variant="danger" onClick={() => handleSubmit("PUBLISHED")}>
          Publish
        </Button>
      </div>
    </div>
  );
};

export default CreatePinForm;
