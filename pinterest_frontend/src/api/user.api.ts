// src/api/user.api.ts
import api from "./axios";

export const userAPI = {
  // REGISTER
  register: (data: { username: string; email: string; password: string }) =>
    api.post("/users/register", data),

  // GET USER BY ID
  getUserById: (id: number) => api.get(`/users/${id}`),

  // DELETE USER
  deleteUser: (id: number) => api.delete(`/users/${id}`),

  // GET USER BY USERNAME
  getByUsername: (username: string) => api.get(`/users/username/${username}`),

  // GET USER BY EMAIL
  getByEmail: (email: string) => api.get(`/users/email/${email}`),

  // ⭐ NEW: REQUEST PASSWORD RESET (ID or email)
  requestPasswordReset: (userIdOrEmail: string) => {
    if (/^\d+$/.test(userIdOrEmail)) {
      // numeric user ID → existing backend route
      return api.post(`/password/reset-request/${Number(userIdOrEmail)}`);
    } else {
      // email-based route → You can add in backend if needed
      return api.post(`/password/reset-request/email`, {
        email: userIdOrEmail,
      });
    }
  },

  // ⭐ NEW: RESET PASSWORD
  resetPassword: (token: string, newPassword: string) =>
    api.post("/password/reset", { token, newPassword }),

  // --------------------------
  // FOLLOW SYSTEM (US-06)
  // --------------------------

  // ✔ follow user
  followUser: (followerId: number, followingId: number) =>
    api.post(`/follow/${followerId}/follow/${followingId}`),

  // ✔ unfollow user
  unfollowUser: (followerId: number, followingId: number) =>
    api.delete(`/follow/${followerId}/unfollow/${followingId}`),

  // ✔ list of followers
  getFollowers: (userId: number) =>
    api.get(`/follow/${userId}/followers`).then(res => res.data),

  // ✔ list of following
  getFollowing: (userId: number) =>
    api.get(`/follow/${userId}/following`).then(res => res.data),

  // -----------------------------
  // INVITATIONS (US-07)
  // -----------------------------
  // Send invitation
  sendInvitation: (payload: {
    senderId: number;
    receiverId: number | null;
    boardId?: number | null;
    invitationType: string; // BOARD_COLLAB | CONNECTION
    message?: string;
  }) => api.post(`/invitations`, payload),

  // Accept invitation
  acceptInvitation: (invitationId: number) =>
    api.post(`/invitations/${invitationId}/accept`),

  // Decline invitation
  declineInvitation: (invitationId: number) =>
    api.post(`/invitations/${invitationId}/decline`),

  // Get invitations sent by user
  getSentInvitations: (userId: number) =>
    api.get(`/invitations/sent/${userId}`).then((res) => res.data),

  // Get invitations received by user
  getReceivedInvitations: (userId: number) =>
    api.get(`/invitations/received/${userId}`).then((res) => res.data),
  getAllBusiness() {
    return api.get("/business").then(res => res.data);
  }

};
