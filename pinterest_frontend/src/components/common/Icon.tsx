import React from "react";

interface IconProps {
  name: string;
  size?: number;
  className?: string;
}

// Map of inline SVG icons
const iconMap: Record<string, React.JSX.Element> = {
  heart: (
    <svg viewBox="0 0 24 24" fill="currentColor">
      <path d="M12 21s-8-6.4-8-11.5S6.5 2 12 7.3C17.5 2 20 4.5 20 9.5S12 21 12 21z" />
    </svg>
  ),

  search: (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
      <circle cx="11" cy="11" r="8"></circle>
      <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
    </svg>
  ),
};

const Icon: React.FC<IconProps> = ({ name, size = 20, className = "" }) => {
  const icon = iconMap[name];

  if (!icon) return null;

  return (
    <span
      className={className}
      style={{
        display: "inline-block",
        width: size,
        height: size,
      }}
    >
      {React.cloneElement(icon, {
        width: size,
        height: size,
      })}
    </span>
  );
};

export default Icon;
