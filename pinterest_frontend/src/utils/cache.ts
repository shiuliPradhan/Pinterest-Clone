// src/utils/cache.ts

/**
 * Simple in-memory + localStorage hybrid cache.
 */

const memoryCache = new Map<string, any>();

export const cache = {
  set(key: string, value: any, persist: boolean = false) {
    memoryCache.set(key, value);
    if (persist) {
      localStorage.setItem(key, JSON.stringify(value));
    }
  },

  get<T>(key: string): T | null {
    if (memoryCache.has(key)) {
      return memoryCache.get(key) as T;
    }
    const raw = localStorage.getItem(key);
    if (!raw) return null;
    try {
      const parsed = JSON.parse(raw);
      memoryCache.set(key, parsed);
      return parsed as T;
    } catch {
      return null;
    }
  },

  remove(key: string) {
    memoryCache.delete(key);
    localStorage.removeItem(key);
  },

  clear() {
    memoryCache.clear();
    localStorage.clear();
  }
};

export default cache;
