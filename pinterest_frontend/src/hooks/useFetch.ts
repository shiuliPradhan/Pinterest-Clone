import { useEffect, useState } from "react";
import axios from "../api/axios";

interface UseFetchOptions {
    auto?: boolean;             // auto fetch on mount (default: true)
    deps?: any[];               // dependency array
}

export const useFetch = <T>(
    url: string,
    options: UseFetchOptions = { auto: true, deps: [] }
) => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<any>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const fetchNow = async () => {
        try {
            setLoading(true);
            setError(null);

            const res = await axios.get(url);
            setData(res.data);
        } catch (err: any) {
            setError(err.response?.data || err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (options.auto) fetchNow();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, options.deps ?? []);

    return { data, error, loading, refetch: fetchNow };
};
