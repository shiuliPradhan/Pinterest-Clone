package com.infy.pinterest.service.business;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.dto.business.CampaignDTO;
import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Campaign;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.business.BusinessProfileRepository;
import com.infy.pinterest.repository.business.CampaignRepository;
import com.infy.pinterest.repository.business.SponsoredPinRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository repo;
    private final BusinessProfileRepository businessRepo;
    private final SponsoredPinRepository sponsoredRepo;
    private final ModelMapper mapper;

    @Override
    public CampaignDTO create(CampaignDTO dto) {
        BusinessProfile bp = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        Campaign c = new Campaign();
        c.setBusiness(bp);
        c.setCampaignName(dto.getCampaignName());
        c.setCampaignDescription(dto.getCampaignDescription());
        c.setBudget(dto.getBudget());
        c.setStartDate(dto.getStartDate());
        c.setEndDate(dto.getEndDate());
        c.setStatus(dto.getStatus() == null ? "ACTIVE" : dto.getStatus());

        Campaign saved = repo.save(c);
        return mapper.map(saved, CampaignDTO.class);
    }

    @Override
    public CampaignDTO getById(Long id) {
        Campaign c = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.CAMPAIGN_NOT_FOUND"));
        return mapper.map(c, CampaignDTO.class);
    }

    @Override
    public CampaignDTO update(Long id, CampaignDTO dto) {
        Campaign c = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.CAMPAIGN_NOT_FOUND"));

        if (dto.getCampaignName() != null) c.setCampaignName(dto.getCampaignName());
        if (dto.getCampaignDescription() != null) c.setCampaignDescription(dto.getCampaignDescription());
        if (dto.getBudget() != null) c.setBudget(dto.getBudget());
        if (dto.getStartDate() != null) c.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) c.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) c.setStatus(dto.getStatus());

        Campaign saved = repo.save(c);
        return mapper.map(saved, CampaignDTO.class);
    }

    @Override
    public List<CampaignDTO> getByBusiness(Long businessId) {
        BusinessProfile bp = businessRepo.findById(businessId)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        return repo.findByBusiness(bp).stream()
                .map(x -> mapper.map(x, CampaignDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Campaign c = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.CAMPAIGN_NOT_FOUND"));

        // remove sponsored pins under this campaign first
        sponsoredRepo.deleteAllByCampaign(c);

        repo.delete(c);
    }
}
