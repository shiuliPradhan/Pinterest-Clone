import api from "./axios";
import { LoginRequest, LoginResponse, RegisterRequest, UserDTO } from "../types/auth";

/**
 * USER REGISTRATION
 * POST /users/register
 */
export const registerUser = async (data: RegisterRequest): Promise<UserDTO> => {
  const res = await api.post<UserDTO>(
    "/users/register", data
  );
  return res.data;
};



/**
 * LOGIN
 * POST /auth/login
 * returns JWT token
 */
export const loginUser = async (data: LoginRequest): Promise<String> => {
  const res = await api.post<String>("/auth/login", null, {
    params: {
      usernameOrEmail: data.usernameOrEmail,
      password: data.password
    }
  });

  return res.data;
};


/**
 * LOGOUT
 * POST /auth/logout
 * backend invalidates JWT token
 */
export const logoutUser = async (token: string): Promise<void> => {
  await api.post(
    "/auth/logout",
    {},
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
};
