import React, { useEffect, useState } from "react";
import { pinAPI } from "../../api/pin.api";
import { useAuth } from "../../hooks/useAuth";

interface Props {
  pinId: number;
}

const SaveButton: React.FC<Props> = ({ pinId }) => {
  const { user } = useAuth();
  const [isSaved, setIsSaved] = useState(false);
  const [saveId, setSaveId] = useState<number | null>(null);

  useEffect(() => {
    if (!user) return;
    if (!user?.id) return;
    pinAPI.getSavedByUser(user.id).then((res) => {
      const found = res.find((s: any) => s.pinId === pinId);
      if (found) {
        setIsSaved(true);
        setSaveId(found.id);
      }
    });
  }, [user, pinId]);

  const handleSave = async () => {
    if (!user) return;

    const res = await pinAPI.savePin({ pinId, userId: user.id });
    setSaveId(res.id);
    setIsSaved(true);
  };

  const handleUnsave = async () => {
    if (!saveId) return;
    await pinAPI.deleteSave(saveId);
    setIsSaved(false);
  };

  return (
    <button
      onClick={isSaved ? handleUnsave : handleSave}
      className={`btn px-4 py-2 fw-semibold rounded-pill text-white border-0`}
      style={{
        backgroundColor: isSaved ? "#E60023" : "rgba(0,0,0,0.75)",
        transition: "background-color 0.2s",
      }}
      onMouseEnter={(e) => {
        if (!isSaved) e.currentTarget.style.backgroundColor = "black";
      }}
      onMouseLeave={(e) => {
        if (!isSaved) e.currentTarget.style.backgroundColor = "rgba(0,0,0,0.75)";
      }}
    >
      {isSaved ? "Saved" : "Save"}
    </button>
  );
};

export default SaveButton;
