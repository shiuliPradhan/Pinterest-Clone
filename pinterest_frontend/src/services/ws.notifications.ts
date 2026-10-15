// src/services/ws.notifications.ts

/**
 * WebSocket wrapper for real-time notifications.
 * Automatically reconnects if connection drops.
 */

export type NotificationMessage = {
  id: number;
  type: string;
  payload: any;
  readFlag: boolean;
  createdAt: string;
};

type WSCallbacks = {
  onMessage?: (msg: NotificationMessage) => void;
  onOpen?: () => void;
  onClose?: () => void;
  onError?: (e: Event) => void;
};

export class NotificationWebSocket {
  private ws?: WebSocket;
  private token: string;
  private reconnectDelay = 3000; // 3 sec
  private heartbeatInterval?: ReturnType<typeof setInterval>;
  private callbacks: WSCallbacks;
  private baseUrl = "ws://localhost:8080/ws/notifications";

  constructor(token: string, callbacks: WSCallbacks = {}) {
    this.token = token;
    this.callbacks = callbacks;
    this.connect();
  }

  private connect() {
    const url = `${this.baseUrl}?token=${this.token}`;
    this.ws = new WebSocket(url);

    this.ws.onopen = () => {
      this.startHeartbeat();
      this.callbacks.onOpen?.();
    };

    this.ws.onmessage = (event) => {
      try {
        const data: NotificationMessage = JSON.parse(event.data);
        this.callbacks.onMessage?.(data);
      } catch (err) {
        console.error("Invalid WS message:", err);
      }
    };

    this.ws.onerror = (e) => {
      this.callbacks.onError?.(e);
    };

    this.ws.onclose = () => {
      this.stopHeartbeat();
      this.callbacks.onClose?.();
      // Auto reconnect
      setTimeout(() => this.connect(), this.reconnectDelay);
    };
  }

  /** Keeps the connection alive */
  private startHeartbeat() {
    this.heartbeatInterval = setInterval(() => {
      if (this.ws && this.ws.readyState === WebSocket.OPEN) {
        this.ws.send(JSON.stringify({ type: "PING" }));
      }
    }, 15000);
  }

  private stopHeartbeat() {
    if (this.heartbeatInterval) clearInterval(this.heartbeatInterval);
  }

  /** Allows UI to send messages (rare for notifications but supported) */
  send(data: any) {
    if (this.ws && this.ws.readyState === WebSocket.OPEN) {
      this.ws.send(JSON.stringify(data));
    }
  }

  /** Cleanup */
  close() {
    this.stopHeartbeat();
    this.ws?.close();
  }
}
