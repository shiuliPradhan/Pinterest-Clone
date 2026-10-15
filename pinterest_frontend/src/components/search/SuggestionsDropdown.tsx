import React, { useEffect, useState } from "react";
import { searchAPI } from "../../api/search.api";

interface Props {
  keyword: string;
  onSelect: (value: string) => void;
}

const SuggestionsDropdown: React.FC<Props> = ({ keyword, onSelect }) => {
  const [suggestions, setSuggestions] = useState<string[]>([]);

  useEffect(() => {
    let isMounted = true;

    const loadSuggestions = async () => {
      try {
        const [pins, tags] = await Promise.all([
          searchAPI.searchPins(keyword),
          searchAPI.getTagSuggestions(keyword),
        ]);

        const pinTitles = pins.map((p: any) => p.title);
        const tagNames = tags.map((t: any) => t.tag);

        const combined = Array.from(new Set([...pinTitles, ...tagNames]));

        if (isMounted) setSuggestions(combined.slice(0, 8));
      } catch (err) {
        console.error("Suggestion error:", err);
      }
    };

    loadSuggestions();
    return () => {
      isMounted = false;
    };
  }, [keyword]);

  if (suggestions.length === 0) return null;

  return (
    <div
      className="position-absolute bg-white rounded-3 shadow border"
      style={{
        top: "100%",
        left: 0,
        right: 0,
        zIndex: 20,
        marginTop: "6px",
      }}
    >
      {suggestions.map((item, index) => (
        <button
          key={index}
          className="dropdown-item text-wrap"
          style={{
            whiteSpace: "normal",
            fontSize: "0.9rem",
            padding: "8px 12px",
          }}
          onClick={() => onSelect(item)}
        >
          {item}
        </button>
      ))}
    </div>
  );
};

export default SuggestionsDropdown;
