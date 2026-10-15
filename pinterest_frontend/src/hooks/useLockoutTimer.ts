import { useState } from "react";

export const usePagination = (initialPage: number = 1, perPage: number = 20) => {
    const [page, setPage] = useState(initialPage);

    const next = () => setPage((p) => p + 1);
    const prev = () => setPage((p) => Math.max(1, p - 1));
    const reset = () => setPage(initialPage);

    const offset = (page - 1) * perPage;

    return { page, perPage, next, prev, reset, offset };
};
