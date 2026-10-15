package com.infy.pinterest.service.business;

import java.util.List;

import com.infy.pinterest.dto.business.BusinessProfileDTO;

public interface BusinessProfileService {
    BusinessProfileDTO create(BusinessProfileDTO dto);
    BusinessProfileDTO update(Long id, BusinessProfileDTO dto);
    BusinessProfileDTO getById(Long id);
    BusinessProfileDTO getByUser(Long userId);
    List<BusinessProfileDTO> getAll();
    void delete(Long id);
}
