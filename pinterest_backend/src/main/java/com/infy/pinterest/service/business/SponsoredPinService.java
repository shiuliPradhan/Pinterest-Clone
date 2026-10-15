package com.infy.pinterest.service.business;

import java.util.List;

import com.infy.pinterest.dto.business.SponsoredPinDTO;

public interface SponsoredPinService {
    SponsoredPinDTO create(SponsoredPinDTO dto);
    SponsoredPinDTO activate(Long id);
    SponsoredPinDTO pause(Long id);
    SponsoredPinDTO updateClickRate(Long id, Double rate);
    List<SponsoredPinDTO> getByCampaign(Long campaignId);
    List<SponsoredPinDTO> getByBusiness(Long businessId);
    List<SponsoredPinDTO> getActive();
    void delete(Long id);
}
