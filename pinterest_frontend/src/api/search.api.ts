import api from "./axios";
import { Pin } from "../types/pin";

class SearchAPI {
  // ➤ Search pins by keyword
  searchPins(keyword: string) {
    return api
      .get<Pin[]>("/pins/search", { params: { keyword } })
      .then(res => res.data);
  }

  // ➤ Get pins by tag
  getPinsByTag(tag: string) {
    return api.get<Pin[]>(`/tags/${tag}`).then(res => res.data);
  }

  // ➤ Tag suggestions for autocomplete
  getTagSuggestions(keyword: string) {
    return api.get(`/tags/${keyword}`).then(res => res.data);
  }
}

export const searchAPI = new SearchAPI();
