import React from "react";

interface Props {
    title: string;
    description: string;
    imageUrl: string;
}

const PinPreview: React.FC<Props> = ({ title, description, imageUrl }) => {
    return (
        <div className="w-full max-w-sm mx-auto bg-white shadow rounded-xl p-4">
            <img
                src={imageUrl || "https://via.placeholder.com/400"}
                alt="Preview"
                className="rounded-xl mb-4 w-full object-cover"
            />

            <h3 className="text-lg font-semibold">{title || "Untitled Pin"}</h3>
            <p className="text-sm text-gray-600 mt-1">
                {description || "No description added yet."}
            </p>
        </div>
    );
};

export default PinPreview;
