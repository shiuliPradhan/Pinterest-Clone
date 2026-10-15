import { useEffect, useRef } from "react";

interface UseInfiniteScrollProps {
    onLoadMore: () => void;   // what to call when bottom is reached
    hasMore: boolean;         // backend says more results available
    threshold?: number;       // trigger distance from bottom (px)
}

export const useInfiniteScroll = ({
    onLoadMore,
    hasMore,
    threshold = 300
}: UseInfiniteScrollProps) => {
    const observerRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        if (!hasMore) return;

        const observer = new IntersectionObserver(
            (entries) => {
                if (entries[0].isIntersecting) {
                    onLoadMore();
                }
            },
            { rootMargin: `0px 0px ${threshold}px 0px` }
        );

        const target = observerRef.current;
        if (target) observer.observe(target);

        return () => {
            if (target) observer.unobserve(target);
        };
    }, [hasMore, onLoadMore, threshold]);

    return { loaderRef: observerRef };
};
