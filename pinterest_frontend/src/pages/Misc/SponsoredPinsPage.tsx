import React, { useEffect, useState } from "react";
import { sponsorsAPI } from "../../api/sponsors.api";
import SponsoredPinCard from "../../components/sponsored/SponsoredPinCard";
import Loader from "../../components/common/Loader";

const SponsoredPinsPage: React.FC = () => {
  const [ads, setAds] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadSponsoredPins();
  }, []);

  const loadSponsoredPins = async () => {
    try {
      setLoading(true);
      const res = await sponsorsAPI.getActiveSponsoredPins();
      setAds(res.data);
    } catch (err) {
      console.error("Failed to load sponsored pins", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-4">

      {/* Heading */}
      <h2 className="fw-bold mb-4">Sponsored Pins</h2>

      {loading ? (
        <Loader />
      ) : ads.length === 0 ? (
        <p className="text-muted mt-4">No active sponsored ads yet.</p>
      ) : (
        <div className="row g-4">
          {ads.map((ad) => (
            <div key={ad.id} className="col-6 col-md-4 col-lg-3 col-xl-2">
              <SponsoredPinCard ad={ad} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SponsoredPinsPage;
