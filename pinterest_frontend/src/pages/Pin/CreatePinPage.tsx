import React from "react";
import CreatePinForm from "../../components/pins/CreatePinForm";
import { pinAPI } from "../../api/pin.api";
import { useAuth } from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";

const CreatePinPage: React.FC = () => {
    const { user } = useAuth();
    const navigate = useNavigate();

    const handleCreate = async (formData: any, isDraft: boolean) => {
        try {
            const payload = {
                ...formData,
                userId: user?.id,
                status: isDraft ? "DRAFT" : "PUBLISHED",
            };

            const result = await pinAPI.createPin(payload);
            navigate(`/pins/${result.data.id}`);
        } catch (err) {
            console.error("Error creating pin", err);
            alert("Failed to create pin");
        }
    };

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-lg-8 col-md-10">
                    
                    <h1 className="fw-bold mb-4">Create a Pin</h1>

                    {/* Bootstrap Card Wrapper */}
                    <div className="card shadow-sm p-4">
                        <CreatePinForm onSubmit={handleCreate} />
                    </div>

                </div>
            </div>
        </div>
    );
};

export default CreatePinPage;
