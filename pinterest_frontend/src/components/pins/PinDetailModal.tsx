import React from "react";
import Modal from "../common/Modal";
import SaveButton from "./SaveButton";
import { pinAPI } from "../../api/pin.api";
import Avatar from "../common/Avatar";

interface Props {
  pin: any;
  isOpen: boolean;
  onClose: () => void;
}

const PinDetailModal: React.FC<Props> = ({ pin, isOpen, onClose }) => {
  if (!pin) return null;

  const handleLike = async () => {
    await pinAPI.likePin(pin.id, pin.user.id);
  };

  const handleUnlike = async () => {
    await pinAPI.unlikePin(pin.id, pin.user.id);
  };

  return (
    <Modal open={isOpen} onClose={onClose}>
      <div className="container-fluid">
        <div className="row g-4">

          {/* Left Image */}
          <div className="col-md-6">
            <img
              src={pin.sourceUrl}
              alt={pin.title}
              className="img-fluid rounded-4 w-100"
              style={{ objectFit: "cover" }}
            />
          </div>

          {/* Right Details */}
          <div className="col-md-6">

            {/* Header Title + Save Button */}
            <div className="d-flex justify-content-between align-items-start mb-3">
              <h2 className="fw-bold">{pin.title}</h2>
              <SaveButton pinId={pin.id} />
            </div>

            {/* Description */}
            <p className="text-muted">{pin.description}</p>

            {/* User */}
            <div className="d-flex align-items-center gap-2 mt-4 mb-3">
              <Avatar size={40} />
              <span className="fw-semibold">{"Unknown User"}</span>
            </div>

            {/* Likes Buttons */}
            <div className="d-flex gap-3 mt-3">
              <button className="btn btn-danger" onClick={handleLike}>
                ❤️ Like
              </button>

              <button className="btn btn-outline-secondary" onClick={handleUnlike}>
                Unlike
              </button>
            </div>
          </div>
        </div>
      </div>
    </Modal>
  );
};

export default PinDetailModal;
