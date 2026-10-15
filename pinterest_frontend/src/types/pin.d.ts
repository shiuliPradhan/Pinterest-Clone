export interface Pin {
  id: number;
  boardId: number;
  userId: number;
  title: string;
  description: string;
  sourceUrl: string;
  isPrivate: boolean;
  status: "DRAFT" | "PUBLISHED";
  createdAt: string;
  updatedAt: string;
}

export interface CreatePinPayload {
  boardId: number;
  userId: number;
  title: string;
  description?: string;
  sourceUrl?: string;
  isPrivate?: boolean;
  status?: "DRAFT" | "PUBLISHED";
}

export interface UpdatePinPayload {
  title?: string;
  description?: string;
  sourceUrl?: string;
  isPrivate?: boolean;
  status?: "DRAFT" | "PUBLISHED";
}
