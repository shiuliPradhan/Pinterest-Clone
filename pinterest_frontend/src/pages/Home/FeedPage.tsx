import React, { useEffect, useState } from "react";
import FeedFilters from "./FeedFilters";
import PinMasonry from "../../components/pins/PinMasonry";
import { pinAPI } from "../../api/pin.api";
import Loader from "../../components/common/Loader";

const FeedPage: React.FC = () => {
  const [pins, setPins] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);

  const [sort, setSort] = useState("latest");
  const [mediaType, setMediaType] = useState("all");

  useEffect(() => {
    loadPins(1, true);
  }, [sort, mediaType]);

  const loadPins = async (pageNumber = 1, reset = false) => {
    try {
      setLoading(true);
      const res = await pinAPI.getPins();
      if (reset) {
        setPins(res.data);
      } else {
        setPins((prev) => [...prev, ...res.data]);
      }
    } catch (e) {
      console.error("Failed to load feed:", e);
    } finally {
      setLoading(false);
    }
  };

  const loadMore = async () => {
    const nextPage = page + 1;
    setPage(nextPage);
    await loadPins(nextPage, false);
  };

  return (
    <div className="container py-4">

      {/* Page Title */}
      <h2 className="fw-bold mb-4">Home Feed</h2>

      {/* Filters */}
      <FeedFilters
        sort={sort}
        onSortChange={setSort}
        mediaType={mediaType}
        onMediaTypeChange={setMediaType}
      />

      {/* Masonry Grid */}
      <PinMasonry pins={pins} />

      {/* Load More Button */}
      <div className="text-center mt-4">
        {loading ? (
          <Loader />
        ) : (
          <button
            onClick={loadMore}
            className="btn btn-dark px-4 py-2"
          >
            Load More
          </button>
        )}
      </div>
    </div>
  );
};

export default FeedPage;
