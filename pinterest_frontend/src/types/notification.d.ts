export interface Notification {
  id: number;
  userId: number;
  type: string;
  payload: any;
  readFlag: boolean;
  createdAt: string;
}
