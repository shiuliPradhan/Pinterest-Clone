import api from "./axios";
import { Notification } from "../types/notification";

const BASE = "/notifications";

class NotificationAPI {
  // ➤ Get notifications for user
  getUserNotifications(userId: number) {
    return api.get<Notification[]>(`${BASE}/user/${userId}`);
  }

  // ➤ Mark a single notification as read
  markAsRead(id: number) {
    return api.post(`${BASE}/${id}/read`, {});
  }

  // ➤ Mark all notifications as read for a user
  markAllAsRead(userId: number) {
    return api.post(`${BASE}/user/${userId}/read-all`, {});
  }

  // ➤ Delete a notification
  deleteNotification(id: number) {
    return api.delete(`${BASE}/${id}`);
  }

  // ➤ Delete all notifications for a user
  clearAll(userId: number) {
    return api.delete(`${BASE}/user/${userId}`);
  }
}

export const notificationAPI = new NotificationAPI();
