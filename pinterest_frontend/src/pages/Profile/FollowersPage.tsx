import React, { useEffect, useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import Avatar from "../../components/common/Avatar";
import Loader from "../../components/common/Loader";

const FollowersPage: React.FC = () => {
  const { user } = useAuth();
  const [followers, setFollowers] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadFollowers();
  }, []);

  const loadFollowers = async () => {
    try {
      const res = await fetch(`/follow/${user?.id}/followers`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });

      const data = await res.json();
      setFollowers(data);
    } catch (err) {
      console.error("Failed to fetch followers", err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader />;

  return (
    <div className="container py-4">
      <h1 className="fw-bold fs-3 mb-4">Followers</h1>

      <div className="mt-3">
        {followers.length === 0 && (
          <p className="text-muted">No followers yet.</p>
        )}

        <div className="list-group">
          {followers.map((f) => (
            <div
              key={f.id}
              className="list-group-item d-flex align-items-center py-3"
            >
              <Avatar size={45} />

              <div className="ms-3">
                <div className="fw-semibold">{f.username}</div>
                <div className="text-muted small">{f.email}</div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default FollowersPage;
