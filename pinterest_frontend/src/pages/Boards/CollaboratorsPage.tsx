import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { boardAPI } from "../../api/board.api";
import { userAPI } from "../../api/user.api";
import Loader from "../../components/common/Loader";

const CollaboratorsPage: React.FC = () => {
    const { id } = useParams();
    const boardId = Number(id);

    const [board, setBoard] = useState<any>(null);
    const [collaborators, setCollaborators] = useState<any[]>([]);
    const [pending, setPending] = useState<any[]>([]);
    const [email, setEmail] = useState("");

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadData = async () => {
            try {
                setLoading(true);

                const boardRes = await boardAPI.getBoard(boardId);
                const boardData = boardRes.data;

                setBoard(boardData);
                setCollaborators(boardData.collaborators || []);

                // Get invitations for this user (board owner)
                const sent = await userAPI.getSentInvitations(boardData.userId);

                setPending(sent.filter((x: any) => x.boardId === boardId));
            } catch (err: any) {
                setError(err.message || "Failed to load collaborators");
            } finally {
                setLoading(false);
            }
        };

        loadData();
    }, [boardId]);

    const handleInvite = async () => {
        if (!email.trim()) return;

        try {
            await userAPI.sendInvitation({ senderId: board.userId, receiverId: null, boardId, invitationType: "BOARD_COLLAB", message: "Join my board!", });

            alert("Invitation sent!");
            setEmail("");
        } catch (err: any) {
            alert(err.message || "Failed to send invitation");
        }
    };

    if (loading) return <Loader />;

    if (error)
        return (
            <div className="alert alert-danger text-center my-4">{error}</div>
        );

    return (
        <div className="container py-4" style={{ maxWidth: "800px" }}>
            <h1 className="fw-bold text-center mb-4">
                Collaborators — {board?.title}
            </h1>

            {/* Invite Section */}
            <div className="card shadow-sm mb-4">
                <div className="card-body">
                    <h5 className="fw-semibold mb-3">Add Collaborator</h5>

                    <div className="d-flex gap-2">
                        <input
                            type="email"
                            className="form-control"
                            placeholder="Enter email address"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <button className="btn btn-primary" onClick={handleInvite}>
                            Invite
                        </button>
                    </div>
                </div>
            </div>

            {/* Collaborators */}
            <div className="card shadow-sm mb-4">
                <div className="card-body">
                    <h5 className="fw-semibold mb-3">Collaborators</h5>

                    {collaborators.length === 0 ? (
                        <p className="text-muted">No collaborators yet.</p>
                    ) : (
                        <ul className="list-group list-group-flush">
                            {collaborators.map((c: any) => (
                                <li key={c.id} className="list-group-item">
                                    <strong>{c.user?.username}</strong> — {c.role}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>

            {/* Pending Invitations */}
            <div className="card shadow-sm">
                <div className="card-body">
                    <h5 className="fw-semibold mb-3">Pending Invitations</h5>

                    {pending.length === 0 ? (
                        <p className="text-muted">No pending invitations.</p>
                    ) : (
                        <ul className="list-group list-group-flush">
                            {pending.map((inv: any) => (
                                <li key={inv.id} className="list-group-item">
                                    To: {inv.receiverEmail} —
                                    <span className="text-muted">{inv.status}</span>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
};

export default CollaboratorsPage;
