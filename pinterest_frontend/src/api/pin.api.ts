import api from "./axios";
import { Pin, CreatePinPayload, UpdatePinPayload } from "../types/pin";

const BASE = "/pins";

class PinAPI {
  // ➤ Get single pin
  getPin(id: number) {
    return api.get<Pin>(`${BASE}/${id}`);
  }

  getPins() {
    return api.get("/pins");
}


  // ➤ Create new pin (supports draft + published)
  createPin(data: CreatePinPayload) {
    return api.post<Pin>(BASE, data);
  }

  // ➤ Update pin
  updatePin(id: number, data: UpdatePinPayload) {
    return api.put<Pin>(`${BASE}/${id}`, data);
  }

  // ➤ Delete pin
  deletePin(id: number) {
    return api.delete(`${BASE}/${id}`);
  }

  // ➤ Search pins
  searchPins(keyword: string) {
    return api.get<Pin[]>(`${BASE}/search`, { params: { keyword } });
  }

  // ➤ Get pins under a board
  getPinsByBoard(boardId: number) {
    return api.get<Pin[]>(`${BASE}/board/${boardId}`);
  }

  /* ========================================================
   *   LIKE APIs
   * ====================================================== */

  likePin(pinId: number, userId: number) {
    return api.post(`/likes/like`, { pinId, userId }).then(res => res.data);
  }

  unlikePin(pinId: number, userId: number) {
    return api.post(`/likes/unlike`, { pinId, userId }).then(res => res.data);
  }

  getLikeCount(pinId: number) {
    return api.get<number>(`/likes/count/${pinId}`).then(res => res.data);
  }

  /* ========================================================
   *   SAVE APIs
   * ====================================================== */

  // Save a pin to a board (optional boardId)
  savePin({ pinId, userId, boardId }: { pinId: number; userId: number; boardId?: number }) {
  return api.post(`/saved`, null, {
    params: { pinId, userId, boardId }
  }).then(res => res.data);
}


  // Get all pins saved by a user
  getSavedByUser(userId: number) {
    return api.get(`/saved/user/${userId}`).then(res => res.data);
  }

  // Delete a saved pin
  deleteSave(saveId: number) {
    return api.delete(`/saved/${saveId}`).then(res => res.data);
  }
}

export const pinAPI = new PinAPI();
