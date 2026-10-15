export interface BoardCollaborator {
  id: number;
  userId: number;
  role: string;
  addedAt: string;
  user?: {
    id: number;
    username: string;
    email: string;
  }
}

export interface Board {
  id: number;
  userId: number;
  title: string;
  description: string | null;
  isPrivate: boolean;
  coverImageUrl: string | null;
  createdAt: string;

  // ➤ ADD THIS
  collaborators?: BoardCollaborator[];
}


export interface CreateBoardRequest {
  userId: number;
  title: string;
  description?: string;
  isPrivate?: boolean;
  coverImageUrl?: string;
}

export interface UpdateBoardRequest {
  title?: string;
  description?: string;
  isPrivate?: boolean;
  coverImageUrl?: string;
}
