import React, { useState, useEffect } from "react";
import { useDebounce } from "../../hooks/useDebounce";
import SuggestionsDropdown from "./SuggestionsDropdown";

interface Props {
  onSearch: (keyword: string) => void;
}

const SearchBar: React.FC<Props> = ({ onSearch }) => {
  const [query, setQuery] = useState("");
  const [showDropdown, setShowDropdown] = useState(false);
  const debounced = useDebounce(query, 300);

  useEffect(() => {
    if (debounced.trim() !== "") {
      onSearch(debounced);
      setShowDropdown(true);
    } else {
      setShowDropdown(false);
    }
  }, [debounced, onSearch]);

  return (
    <div className="position-relative w-100" style={{ maxWidth: "550px" }}>
      <input
        type="text"
        value={query}
        placeholder="Search for ideas, outfits, recipes..."
        onChange={(e) => setQuery(e.target.value)}
        className="form-control rounded-pill shadow-sm px-4 py-2"
        style={{
          borderColor: "#ddd",
          transition: "0.2s",
        }}
        onFocus={() => {
          if (query.trim() !== "") setShowDropdown(true);
        }}
      />

      {showDropdown && query.trim() !== "" && (
        <SuggestionsDropdown
          keyword={query}
          onSelect={(word) => {
            setQuery(word);
            onSearch(word);
            setShowDropdown(false);
          }}
        />
      )}
    </div>
  );
};

export default SearchBar;
