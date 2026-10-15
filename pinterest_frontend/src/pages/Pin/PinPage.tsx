import React, { useEffect, useState } from "react";
import { pinAPI } from "../../api/pin.api";
import { useParams } from "react-router-dom";
import PinDetailModal from "../../components/pins/PinDetailModal";
import Loader from "../../components/common/Loader";

const PinPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [pin, setPin] = useState<any>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadPin();
    }, [id]);

    const loadPin = async () => {
        try {
            const data = await pinAPI.getPin(Number(id));
            setPin(data.data);
        } catch (err) {
            console.error("Failed to load pin", err);
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <Loader />;
    if (!pin) return <p className="p-4 text-center">Pin not found.</p>;

    return (
        <div className="container py-4 d-flex justify-content-center">
            {/* Modal always open */}
            <PinDetailModal pin={pin} onClose={() => {}} isOpen={true} />
        </div>
    );
};

export default PinPage;
