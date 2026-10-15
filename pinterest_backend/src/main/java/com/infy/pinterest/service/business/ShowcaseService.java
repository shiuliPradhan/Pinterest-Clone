package com.infy.pinterest.service.business;

import java.util.List;

import com.infy.pinterest.dto.business.ShowcaseDTO;

public interface ShowcaseService {
    ShowcaseDTO create(ShowcaseDTO dto);
    ShowcaseDTO update(Long id, ShowcaseDTO dto);
    ShowcaseDTO getById(Long id);
    List<ShowcaseDTO> getByBusiness(Long businessId);
    void delete(Long id);
}