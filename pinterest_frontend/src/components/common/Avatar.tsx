import React from "react";

interface AvatarProps {
  src?: string;
  alt?: string;
  size?: number; // px
  className?: string;
}

const Avatar: React.FC<AvatarProps> = ({ src, alt = "avatar", size = 20, className }) => {
  return (
    <img
      src={src || "/assets/icons/avatar.svg"}
      alt={alt}
      width={size}
      height={size}
      className={`rounded-circle ${className || ""}`}
      style={{
        width: size,
        height: size,
        objectFit: "cover",
      }}
    />
  );
};

export default Avatar;
