export interface User {
  id: number;
  email: string;
  username: string;
  roles: string;
}

export interface UserProfile {
  id: number;
  userId: number;
  displayName: string | null;
  bio: string | null;
  profileImageUrl: string | null;
  phoneNumber: string | null;
  websiteUrl: string | null;
  location: string | null;
}

export interface CreateUserRequest {
  email: string;
  username: string;
  password: string;
}

export interface UpdateProfileRequest {
  displayName?: string;
  bio?: string;
  profileImageUrl?: string;
  phoneNumber?: string;
  websiteUrl?: string;
  location?: string;
}

export interface BusinessProfile {
  id: number;
  userId: number;
  businessName: string;
  logoUrl: string | null;
  description: string | null;
  websiteUrl: string | null;
  createdAt: string;
}

export interface Showcase {
  id: number;
  businessId: number;
  title: string;
  description: string | null;
  coverImageUrl: string | null;
  createdAt: string;
}

