import React, { useEffect, useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import Avatar from "../../components/common/Avatar";
import Loader from "../../components/common/Loader";
import BoardList from "../../components/boards/BoardList";
import PinGrid from "../../components/pins/PinGrid";
import { boardAPI } from "../../api/board.api";
import { pinAPI } from "../../api/pin.api";

const ProfilePage: React.FC = () => {
  const { user } = useAuth();

  const [profile, setProfile] = useState<any>(null);
  const [pins, setPins] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, [user?.id]);

  const loadData = async () => {
    try {
      const token = localStorage.getItem("token");

      // Load profile
      const pRes = await fetch(`/profiles/user/${user?.id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      const pData = await pRes.json();
      setProfile(pData);

      // Load pins from all boards
      let collectedPins: any[] = [];
      for (let b of pData.data.boards) {
        try {
          const boardPins = await pinAPI.getPinsByBoard(b.id);
          collectedPins.push(...boardPins.data);
        } catch {}
      }
      setPins(collectedPins);
    } catch (err) {
      console.error("Failed to load profile data", err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader />;
  if (!profile) return <p className="p-4">Profile not found</p>;

  return (
    <div className="container py-4" style={{ maxWidth: "900px" }}>
      
      {/* PROFILE HEADER */}
      <div className="text-center">
        <Avatar size={110} />

        <h1 className="fw-bold fs-2 mt-3">
          {profile.displayName || user?.username}
        </h1>

        <p className="text-muted">{profile.bio}</p>

        <div className="d-flex justify-content-center gap-4 mt-3">
          <a className="text-decoration-none fw-semibold" href="/profile/followers">
            Followers
          </a>
          <a className="text-decoration-none fw-semibold" href="/profile/following">
            Following
          </a>
        </div>

        <button className="btn btn-dark rounded-pill px-4 mt-4">
          Edit Profile
        </button>
      </div>

      {/* BOARDS */}
      <section className="mt-5">
        <h2 className="fw-bold fs-4 mb-3">Boards</h2>
        <BoardList userId={user!.id} />
      </section>

      {/* PINS */}
      <section className="mt-5">
        <h2 className="fw-bold fs-4 mb-3">Pins</h2>
        <PinGrid pins={pins} />
      </section>
    </div>
  );
};

export default ProfilePage;
