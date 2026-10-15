import React, { useEffect } from "react";

interface ModalProps {
  open: boolean;
  onClose: () => void;
  children: React.ReactNode;
  className?: string;
}

const Modal: React.FC<ModalProps> = ({ open, onClose, children, className }) => {
  // Close on ESC
  useEffect(() => {
    const handler = (e: KeyboardEvent) => {
      if (e.key === "Escape") onClose();
    };
    if (open) document.addEventListener("keydown", handler);
    return () => document.removeEventListener("keydown", handler);
  }, [open, onClose]);

  if (!open) return null;

  return (
    <div
      className="modal fade show d-block"
      tabIndex={-1}
      onClick={onClose}
      style={{ backgroundColor: "rgba(0,0,0,0.6)" }}
    >
      <div
        className="modal-dialog modal-dialog-centered"
        onClick={(e) => e.stopPropagation()}
      >
        <div className={`modal-content rounded-4 p-3 shadow ${className || ""}`}>
          {children}
        </div>
      </div>
    </div>
  );
};

export default Modal;
