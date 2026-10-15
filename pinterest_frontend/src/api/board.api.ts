import api from "./axios";
import { Board, CreateBoardRequest, UpdateBoardRequest } from "../types/board";

const BASE = "/boards";

class BoardAPI {
  // ➤ Create a new board
  createBoard(data: CreateBoardRequest) {
    return api.post<Board>(BASE, data);
  }

  // ➤ Get board by ID
  getBoard(id: number) {
    return api.get<Board>(`${BASE}/${id}`);
  }

  // ➤ Update a board
  updateBoard(id: number, data: UpdateBoardRequest) {
    return api.put<Board>(`${BASE}/${id}`, data);
  }

  // ➤ Delete board
  deleteBoard(id: number) {
    return api.delete(`${BASE}/${id}`);
  }

  // ➤ Fetch boards created by a user
  getBoardsByUser(userId: number) {
    return api.get<Board[]>(`${BASE}/user/${userId}`);
  }

  // ➤ Search boards by keyword
  searchBoards(keyword: string) {
    return api.get<Board[]>(`${BASE}/search`, {
      params: { keyword },
    });
  }
}

export const boardAPI = new BoardAPI();
