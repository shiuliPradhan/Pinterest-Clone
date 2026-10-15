import { useState } from "react";
import { userAPI } from "../../api/user.api";
import { useAuth } from "../../hooks/useAuth";

interface Props {
  targetUserId: number;
  initialIsFollowing: boolean;
}

const FollowButton: React.FC<Props> = ({ targetUserId, initialIsFollowing }) => {
  const { user } = useAuth();
  const [isFollowing, setIsFollowing] = useState(initialIsFollowing);
  const [loading, setLoading] = useState(false);

  if (!user) return null;

  const handleFollowToggle = async () => {
    setLoading(true);

    try {
      if (isFollowing) {
        await userAPI.unfollowUser(user.id, targetUserId);
        setIsFollowing(false);
      } else {
        await userAPI.followUser(user.id, targetUserId);
        setIsFollowing(true);
      }
    } catch (err) {
      console.error("Follow action failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      onClick={handleFollowToggle}
      disabled={loading}
      className={`btn rounded-pill px-3 py-1 fw-semibold`}
      style={{
        backgroundColor: isFollowing ? "#e0e0e0" : "#E60023",
        color: isFollowing ? "#000" : "#fff",
        transition: "0.2s",
        opacity: loading ? 0.7 : 1,
      }}
      onMouseEnter={(e) => {
        if (!loading && isFollowing) e.currentTarget.style.backgroundColor = "#cfcfcf";
      }}
      onMouseLeave={(e) => {
        if (!loading && isFollowing) e.currentTarget.style.backgroundColor = "#e0e0e0";
      }}
    >
      {loading ? "..." : isFollowing ? "Following" : "Follow"}
    </button>
  );
};

export default FollowButton;
