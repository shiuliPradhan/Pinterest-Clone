import React, { createContext, useContext, useState } from "react";
import { useAuth } from "./AuthContext";

interface NotificationContextType {
    notifications: any[];
    unreadCount: number;
    refresh: () => void;
    markAsRead: (id: number) => Promise<void>;
}

const NotificationContext = createContext<NotificationContextType>(
    {} as NotificationContextType
);

export const NotificationProvider = ({ children }: { children: React.ReactNode }) => {
    const { user } = useAuth();

    // Empty notifications because backend is not implemented
    const [notifications] = useState<any[]>([]);

    // Dummy functions to avoid crashing
    const refresh = () => {
        console.warn("Notifications disabled — no backend controller.");
    };

    const markAsRead = async () => {
        console.warn("markAsRead disabled — no backend controller.");
    };

    return (
        <NotificationContext.Provider
            value={{
                notifications,
                unreadCount: 0,
                refresh,
                markAsRead,
            }}
        >
            {children}
        </NotificationContext.Provider>
    );
};

export const useNotifications = () => useContext(NotificationContext);
