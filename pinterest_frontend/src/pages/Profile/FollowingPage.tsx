import React, { useEffect, useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import Avatar from "../../components/common/Avatar";
import Loader from "../../components/common/Loader";

const FollowingPage: React.FC = () => {
  const { user } = useAuth();
  const [following, setFollowing] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadFollowing();
  }, []);

  const loadFollowing = async () => {
    try {
      const res = await fetch(`/follow/${user?.id}/following`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });

      const data = await res.json();
      setFollowing(data);
    } catch (err) {
      console.error("Failed to fetch following list", err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader />;

  return (
    <div className="container py-4">
      <h1 className="fw-bold fs-3 mb-4">Following</h1>

      {following.length === 0 && (
        <p className="text-muted">You are not following anyone yet.</p>
      )}

      <div className="list-group">
        {following.map((u) => (
          <div
            key={u.id}
            className="list-group-item d-flex align-items-center py-3"
          >
            <Avatar size={45} />

            <div className="ms-3">
              <div className="fw-semibold">{u.username}</div>
              <div className="text-muted small">{u.email}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FollowingPage;
