package com.infy.pinterest.service.business;
import java.util.List;

import com.infy.pinterest.dto.business.CampaignDTO;

public interface CampaignService {
    CampaignDTO create(CampaignDTO dto);
    CampaignDTO update(Long id, CampaignDTO dto);
    CampaignDTO getById(Long id);
    List<CampaignDTO> getByBusiness(Long businessId);
    void delete(Long id);
}
