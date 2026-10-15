import React from "react";

interface FeedFiltersProps {
  sort: string;
  onSortChange: (v: string) => void;
  mediaType: string;
  onMediaTypeChange: (v: string) => void;
}

const FeedFilters: React.FC<FeedFiltersProps> = ({
  sort,
  onSortChange,
  mediaType,
  onMediaTypeChange
}) => {
  return (
    <div className="d-flex flex-wrap align-items-center gap-3 mb-4">
      {/* Sort Filter */}
      <select
        value={sort}
        onChange={(e) => onSortChange(e.target.value)}
        className="form-select w-auto shadow-sm"
      >
        <option value="latest">Latest</option>
        <option value="popular">Most Popular</option>
        <option value="oldest">Oldest</option>
      </select>

      {/* Media Type Filter */}
      <select
        value={mediaType}
        onChange={(e) => onMediaTypeChange(e.target.value)}
        className="form-select w-auto shadow-sm"
      >
        <option value="all">All</option>
        <option value="image">Images</option>
        <option value="video">Videos</option>
      </select>
    </div>
  );
};

export default FeedFilters;
