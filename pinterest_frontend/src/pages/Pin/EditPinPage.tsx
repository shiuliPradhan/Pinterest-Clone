import React, { useEffect, useState } from "react";
import { pinAPI } from "../../api/pin.api";
import CreatePinForm from "../../components/pins/CreatePinForm";
import { useParams, useNavigate } from "react-router-dom";

const EditPinPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

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

    const handleUpdate = async (formData: any, isDraft: boolean) => {
        try {
            const payload = {
                ...formData,
                status: isDraft ? "DRAFT" : "PUBLISHED",
            };

            await pinAPI.updatePin(Number(id), payload);
            navigate(`/pins/${id}`);
        } catch (err) {
            console.error("Error updating pin", err);
            alert("Failed to update pin");
        }
    };

    if (loading) return <p className="p-4 text-center">Loading...</p>;
    if (!pin) return <p className="p-4 text-center">Pin not found</p>;

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-lg-8 col-md-10">

                    <h1 className="fw-bold mb-4">Edit Pin</h1>

                    <div className="card shadow-sm p-4">
                        <CreatePinForm onSubmit={handleUpdate} />
                    </div>

                </div>
            </div>
        </div>
    );
};

export default EditPinPage;
