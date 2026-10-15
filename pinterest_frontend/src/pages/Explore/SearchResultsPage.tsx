import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { searchAPI } from "../../api/search.api";
import PinGrid from "../../components/pins/PinGrid";
import Loader from "../../components/common/Loader";

const SearchResultsPage: React.FC = () => {
  const [params] = useSearchParams();
  const keyword = params.get("query") || "";

  const [results, setResults] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (keyword.trim().length > 0) fetchResults(keyword);
  }, [keyword]);

  const fetchResults = async (kw: string) => {
    try {
      setLoading(true);
      const data = await searchAPI.searchPins(kw);
      setResults(data);
    } catch (err) {
      console.error("Search failed:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-4">
      {/* Heading */}
      <h3 className="fw-bold mb-3">
        Search results for:{" "}
        <span className="text-danger">{keyword}</span>
      </h3>

      {/* Content */}
      {loading ? (
        <Loader />
      ) : results.length === 0 ? (
        <p className="text-muted">No results found.</p>
      ) : (
        <PinGrid pins={results} />
      )}
    </div>
  );
};

export default SearchResultsPage;
