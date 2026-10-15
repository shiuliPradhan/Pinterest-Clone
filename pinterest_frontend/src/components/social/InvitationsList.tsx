import { useEffect, useState } from "react";
import { userAPI } from "../../api/user.api";
import Button from "../common/Button";

interface Props {
  userId: number;
  mode: "sent" | "received";
}

const InvitationsList: React.FC<Props> = ({ userId, mode }) => {
  const [list, setList] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  const loadData = async () => {
    try {
      const data =
        mode === "sent"
          ? await userAPI.getSentInvitations(userId)
          : await userAPI.getReceivedInvitations(userId);

      setList(data);
    } catch (err) {
      console.error("Invitation fetch error", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [userId, mode]);

  const handleAction = async (id: number, action: "accept" | "decline") => {
    try {
      if (action === "accept") await userAPI.acceptInvitation(id);
      else await userAPI.declineInvitation(id);

      loadData(); // refresh
    } catch (err) {
      console.error("Invitation action failed:", err);
    }
  };

  if (loading) return <p className="text-center py-3">Loading...</p>;

  if (list.length === 0)
    return (
      <p className="text-center text-muted py-3">
        No {mode === "sent" ? "sent" : "received"} invitations
      </p>
    );

  return (
    <div className="d-flex flex-column gap-3">
      {list.map((inv) => (
        <div
          key={inv.id}
          className="bg-white p-3 rounded-3 shadow-sm d-flex justify-content-between align-items-center"
        >
          {/* Invitation Info */}
          <div>
            <p className="fw-semibold mb-1">
              Invitation from {inv.senderName || "User"}
            </p>
            <small className="text-muted">
              {inv.message || "No message"}
            </small>
          </div>

          {/* Buttons when receiving invitations */}
          {mode === "received" && (
            <div className="d-flex gap-2">
              <Button
                variant="primary"
                onClick={() => handleAction(inv.id, "accept")}
                className="btn-success text-white"
              >
                Accept
              </Button>

              <Button
                variant="secondary"
                onClick={() => handleAction(inv.id, "decline")}
                className="btn-outline-secondary"
              >
                Decline
              </Button>
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default InvitationsList;
