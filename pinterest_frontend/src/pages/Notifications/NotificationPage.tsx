import React, { useEffect, useState } from "react";
import { notificationAPI } from "../../api/notification.api";
import { useAuth } from "../../hooks/useAuth";
import Loader from "../../components/common/Loader";

const NotificationPage: React.FC = () => {
  const { user } = useAuth();
  const userId = user?.id;

  const [notifications, setNotifications] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (userId) loadNotifications();
  }, [userId]);

  const loadNotifications = async () => {
    try {
      setLoading(true);
      const data = await notificationAPI.getUserNotifications(userId);
      setNotifications(data.data);
    } catch (err) {
      console.error("Failed to fetch notifications", err);
    } finally {
      setLoading(false);
    }
  };

  const handleMarkRead = async (notifId: number) => {
    try {
      await notificationAPI.markAsRead(notifId);
      setNotifications((prev) =>
        prev.map((n) =>
          n.id === notifId ? { ...n, readFlag: true } : n
        )
      );
    } catch (err) {
      console.error("Failed to mark notification as read", err);
    }
  };

  if (!userId) {
    return (
      <div className="container py-4 text-center">
        <p className="text-muted">Login to view notifications.</p>
      </div>
    );
  }

  return (
    <div className="container py-4">

      <h2 className="fw-bold mb-4">Notifications</h2>

      {loading ? (
        <Loader />
      ) : notifications.length === 0 ? (
        <p className="text-muted">No notifications yet.</p>
      ) : (
        <div className="d-flex flex-column gap-3">
          {notifications.map((notif) => (
            <div
              key={notif.id}
              className={`card shadow-sm border ${
                notif.readFlag ? "bg-light border-secondary" : "bg-white border-dark"
              }`}
            >
              <div className="card-body d-flex justify-content-between align-items-start">

                <div>
                  <h5 className="card-title fw-semibold">
                    {notif.type || "Notification"}
                  </h5>
                  <p className="card-text">
                    {notif.payload?.message ||
                      JSON.stringify(notif.payload)}
                  </p>
                  <small className="text-muted">
                    {new Date(notif.createdAt).toLocaleString()}
                  </small>
                </div>

                {!notif.readFlag && (
                  <button
                    onClick={() => handleMarkRead(notif.id)}
                    className="btn btn-dark btn-sm"
                  >
                    Mark Read
                  </button>
                )}

              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default NotificationPage;
