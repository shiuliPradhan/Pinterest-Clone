import React, { createContext, useContext, useState } from "react";

interface UIContextType {
    loading: boolean;
    setLoading: (state: boolean) => void;

    modalContent: React.ReactNode | null;
    openModal: (content: React.ReactNode) => void;
    closeModal: () => void;

    toast: { message: string; type: "success" | "error" } | null;
    showToast: (msg: string, type?: "success" | "error") => void;
}

const UIContext = createContext<UIContextType>({} as UIContextType);

export const UIProvider = ({ children }: { children: React.ReactNode }) => {
    const [loading, setLoading] = useState(false);
    const [modalContent, setModalContent] = useState<React.ReactNode | null>(null);
    const [toast, setToast] = useState<{
        message: string;
        type: "success" | "error";
    } | null>(null);

    const openModal = (content: React.ReactNode) => setModalContent(content);
    const closeModal = () => setModalContent(null);

    const showToast = (message: string, type: "success" | "error" = "success") => {
        setToast({ message, type });
        setTimeout(() => setToast(null), 2500);
    };

    return (
        <UIContext.Provider
            value={{
                loading,
                setLoading,
                modalContent,
                openModal,
                closeModal,
                toast,
                showToast,
            }}
        >
            {children}
        </UIContext.Provider>
    );
};

export const useUI = () => useContext(UIContext);
