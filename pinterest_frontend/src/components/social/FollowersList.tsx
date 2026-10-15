import { useEffect, useState } from "react";
import { userAPI } from "../../api/user.api";
import Avatar from "../common/Avatar";

interface Props {
  userId: number;
  mode: "followers" | "following";
}

const FollowersList: React.FC<Props> = ({ userId, mode }) => {
  const [list, setList] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const load = async () => {
      try {
        const data =
          mode === "followers"
            ? await userAPI.getFollowers(userId)
            : await userAPI.getFollowing(userId);

        setList(data);
      } catch (err) {
        console.error("Failed to load follow data", err);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [userId, mode]);

  if (loading)
    return <p className="text-center py-3">Loading...</p>;

  if (list.length === 0)
    return (
      <p className="text-center text-muted py-3">
        No {mode === "followers" ? "followers" : "following"} yet
      </p>
    );

  return (
    <div className="d-flex flex-column gap-3">
      {list.map((u) => (
        <div
          key={u.id}
          className="d-flex align-items-center gap-3 bg-white p-3 rounded-3 shadow-sm"
        >
          <Avatar src={u.profileImageUrl} size={40} />

          <div className="d-flex flex-column">
            <span className="fw-semibold">{u.username}</span>
            <small className="text-muted">{u.email}</small>
          </div>
        </div>
      ))}
    </div>
  );
};

export default FollowersList;
