import React, { useEffect, useState } from "react";
import SearchBar from "../../components/search/SearchBar";
import PinGrid from "../../components/pins/PinGrid";
import { searchAPI } from "../../api/search.api";
import Loader from "../../components/common/Loader";

const TRENDING_KEYWORDS = [
  "fashion",
  "travel",
  "food",
  "fitness",
  "technology",
  "home decor",
  "art",
  "outfit",
  "wedding",
  "motivation"
];

const ExplorePage: React.FC = () => {
  const [trendingPins, setTrendingPins] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  const keyword = (k: string) => {
    window.location.href = `/explore/search?query=${encodeURIComponent(k)}`;
  };

  useEffect(() => {
    loadTrending();
  }, []);

  const loadTrending = async () => {
    try {
      setLoading(true);

      const randomKeyword =
        TRENDING_KEYWORDS[Math.floor(Math.random() * TRENDING_KEYWORDS.length)];

      const result = await searchAPI.searchPins(randomKeyword);
      setTrendingPins(result);
    } catch (err) {
      console.error("Failed to load trending:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-4">
      {/* Heading */}
      <h1 className="fw-bold text-center mb-4">Explore</h1>

      {/* Search Bar */}
      <div className="mb-4">
        <div className="mx-auto" style={{ maxWidth: "600px" }}>
          <SearchBar onSearch={keyword} />
        </div>
      </div>

      {/* Trending Pins */}
      <h4 className="fw-semibold mb-3">Trending Ideas</h4>

      {loading ? (
        <Loader />
      ) : (
        <PinGrid pins={trendingPins} />
      )}

      {/* Categories */}
      <h4 className="fw-semibold mt-4 mb-3">Explore Categories</h4>

      <div className="d-flex flex-wrap gap-2">
        {TRENDING_KEYWORDS.map((k) => (
          <a
            key={k}
            href={`/explore/search?query=${encodeURIComponent(k)}`}
            className="badge rounded-pill bg-light border px-3 py-2 text-dark text-decoration-none"
            style={{ fontSize: "0.9rem", cursor: "pointer" }}
          >
            {k}
          </a>
        ))}
      </div>
    </div>
  );
};

export default ExplorePage;
