import React, { useEffect, useState } from "react";
import { userAPI } from "../../api/user.api";
import { sponsorsAPI } from "../../api/sponsors.api";

import BusinessProfileHeader from "../../components/business/BusinessProfileHeader";
import ShowcaseCarousel from "../../components/business/ShowcaseCarousel";
import SponsoredPinCard from "../../components/sponsored/SponsoredPinCard";
import Loader from "../../components/common/Loader";

const BusinessDirectoryPage: React.FC = () => {
  const [businesses, setBusinesses] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [expandedBusinessId, setExpandedBusinessId] = useState<number | null>(null);
  const [showcases, setShowcases] = useState<any[]>([]);
  const [ads, setAds] = useState<any[]>([]);
  const [loadingDetails, setLoadingDetails] = useState(false);

  useEffect(() => {
    const loadBusinesses = async () => {
      try {
        setLoading(true);
        const list = await userAPI.getAllBusiness();
        setBusinesses(list);
      } catch (err: any) {
        setError(err.message || "Failed to load business directory");
      } finally {
        setLoading(false);
      }
    };

    loadBusinesses();
  }, []);

  const loadBusinessDetails = async (businessId: number) => {
    setExpandedBusinessId((prev) => (prev === businessId ? null : businessId));

    if (expandedBusinessId === businessId) return; // collapse

    try {
      setLoadingDetails(true);

      const showcaseList = await sponsorsAPI.getShowcasesByBusiness(businessId);
      const sponsoredList = await sponsorsAPI.getSponsoredPinsByBusiness(businessId);

      setShowcases(showcaseList.data);
      setAds(sponsoredList);
    } catch (err: any) {
      setError("Failed to load business details");
    } finally {
      setLoadingDetails(false);
    }
  };

  if (loading) return <Loader />;

  if (error)
    return <div className="alert alert-danger text-center my-4">{error}</div>;

  return (
    <div className="container py-4">
      <h1 className="fw-bold text-center mb-4">Business Directory</h1>

      <div className="d-flex flex-column gap-4">
        {businesses.map((b) => (
          <div
            key={b.id}
            className="card shadow-sm border-0 rounded-4 p-4"
            style={{ cursor: "pointer" }}
          >
            {/* Business Header */}
            <div onClick={() => loadBusinessDetails(b.id)}>
              <BusinessProfileHeader business={b} />
            </div>

            {/* Expanded section */}
            {expandedBusinessId === b.id && (
              <div className="mt-4">
                {loadingDetails ? (
                  <Loader />
                ) : (
                  <>
                    {/* Showcase */}
                    <h5 className="fw-semibold mb-2">Showcases</h5>

                    {showcases.length === 0 ? (
                      <small className="text-muted">No showcases available.</small>
                    ) : (
                      <ShowcaseCarousel showcases={showcases} />
                    )}

                    {/* Sponsored Ads */}
                    <h5 className="fw-semibold mt-4 mb-2">Sponsored Pins</h5>

                    {ads.length === 0 ? (
                      <small className="text-muted">No sponsored content yet.</small>
                    ) : (
                      <div className="row g-3">
                        {ads.map((ad: any) => (
                          <div key={ad.id} className="col-6 col-md-4 col-lg-3">
                            <SponsoredPinCard ad={ad} />
                          </div>
                        ))}
                      </div>
                    )}
                  </>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default BusinessDirectoryPage;
