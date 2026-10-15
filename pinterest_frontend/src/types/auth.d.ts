export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface RegisterRequest {
  email: string;
  username: string;
  password: string;
}

export interface UserDTO {
  id: number;
  email: string;
  username: string;
  roles: string;
}
