import { useEffect, useState } from "react";

export const useLockoutTimer = (initialSeconds: number = 0) => {
    const [seconds, setSeconds] = useState<number>(initialSeconds);

    useEffect(() => {
        if (seconds <= 0) return;

        const id = setInterval(() => {
            setSeconds((s) => s - 1);
        }, 1000);

        return () => clearInterval(id);
    }, [seconds]);

    const start = (duration: number) => setSeconds(duration);
    const reset = () => setSeconds(0);

    return {
        seconds,
        isLocked: seconds > 0,
        start,
        reset
    };
};
