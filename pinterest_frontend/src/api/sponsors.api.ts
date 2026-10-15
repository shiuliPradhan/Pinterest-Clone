import api from "./axios";

const BASE = "/ads";

class SponsorsAPI {
  // ➤ Create sponsored ad
  createAd(data: any) {
    return api.post(`${BASE}`, data);
  }

  // ➤ Pause ad
  pauseAd(id: number) {
    return api.post(`${BASE}/${id}/pause`);
  }

  // ➤ Activate ad
  activateAd(id: number) {
    return api.post(`${BASE}/${id}/activate`);
  }

  // ➤ Update click rate
  updateClickRate(id: number, newRate: number) {
    return api.post(`${BASE}/${id}/click-rate`, { clickRate: newRate });
  }

  // ➤ Get ads by campaign
  getAdsByCampaign(campaignId: number) {
    return api.get(`${BASE}/campaign/${campaignId}`);
  }

  // ➤ Get ads by business
  getAdsByBusiness(businessId: number) {
    return api.get(`${BASE}/business/${businessId}`);
  }

  // ➤ Get *active* sponsored ads (US09)
  getActiveSponsoredPins() {
    return api.get(`${BASE}/active`);
  }

  // ----------- EXTRA ENDPOINTS USED BY YOUR FRONTEND -----------

  // ➤ Get showcases by business
  getShowcasesByBusiness(businessId: number) {
    return api.get(`/showcases/business/${businessId}`);
  }

  // ➤ Get sponsored pins by business (derived from getAdsByBusiness)
  async getSponsoredPinsByBusiness(businessId: number) {
    const res = await this.getAdsByBusiness(businessId);
    return res.data;
  }

  // ➤ Delete ad
  deleteAd(id: number) {
    return api.delete(`${BASE}/${id}`);
  }
}

export const sponsorsAPI = new SponsorsAPI();
