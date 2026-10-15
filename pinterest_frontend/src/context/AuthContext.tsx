import React, { createContext, useContext, useEffect, useState } from "react";
import { loginUser } from "../api/auth.api";
import { userAPI } from "../api/user.api";

interface AuthContextType {
    user: any | null;
    token: string | null;
    loading: boolean;

    login: (username: string, password: string) => Promise<void>;
    logout: () => void;

    setUser: React.Dispatch<React.SetStateAction<any | null>>;
    setToken: React.Dispatch<React.SetStateAction<string | null>>;
}

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {

    /** Load token + user from localStorage */
    const [token, setToken] = useState<string | null>(
        localStorage.getItem("token")
    );

    const [user, setUser] = useState<any | null>(
        JSON.parse(localStorage.getItem("user") || "null")
    );

    const [loading, setLoading] = useState(true);

    /** Decode username from JWT (sub) */
    const extractUsername = (tokenStr: string): string | null => {
        try {
            const payload = JSON.parse(atob(tokenStr.split(".")[1]));
            return payload.sub ?? null;
        } catch {
            return null;
        }
    };

    /** Load user from backend only if needed */
    useEffect(() => {
        const loadUser = async () => {
            // No token → no user
            if (!token) {
                setUser(null);
                setLoading(false);
                return;
            }

            // User already restored from localStorage → skip fetch
            if (user) {
                setLoading(false);
                return;
            }

            const username = extractUsername(token);
            if (!username) {
                setLoading(false);
                return;
            }

            try {
                const data = await userAPI.getByUsername(username);

                setUser(data.data);
                localStorage.setItem("user", JSON.stringify(data.data));
            } catch {
                // token invalid or user not found
                setUser(null);
                localStorage.removeItem("user");
            }

            setLoading(false);
        };

        loadUser();
    }, [token]);

    /** LOGIN HANDLER */
    const login = async (usernameOrEmail: string, password: string) => {
        const jwt = await loginUser({ usernameOrEmail, password });

        localStorage.setItem("token", jwt.trim());
        setToken(jwt.trim());

        // Extract username from token
        const username = extractUsername(jwt.trim());
        if (!username) {
            throw new Error("Invalid JWT: username not found in token");
        }
        // Fetch full user from backend
        const full = await userAPI.getByUsername(username);

        setUser(full.data);
        localStorage.setItem("user", JSON.stringify(full.data));
    };


    /** LOGOUT HANDLER */
    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        setToken(null);
        setUser(null);
        window.location.href = "/auth/signin";
    };

    return (
        <AuthContext.Provider
            value={{ user, token, loading, login, logout, setUser, setToken }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
