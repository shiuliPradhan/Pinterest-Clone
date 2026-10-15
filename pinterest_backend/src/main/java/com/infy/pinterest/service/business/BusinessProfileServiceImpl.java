package com.infy.pinterest.service.business;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.dto.business.BusinessProfileDTO;
import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.business.BusinessProfileRepository;
import com.infy.pinterest.repository.business.CampaignRepository;
import com.infy.pinterest.repository.business.ShowcaseRepository;
import com.infy.pinterest.repository.business.SponsoredPinRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private final BusinessProfileRepository repo;
    private final UserRepository userRepo;
    private final ShowcaseRepository showcaseRepo;
    private final CampaignRepository campaignRepo;
    private final SponsoredPinRepository sponsoredRepo;
    private final ModelMapper mapper;

    @Override
    public BusinessProfileDTO create(BusinessProfileDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        // prevent duplicate business for same user (depending on SRS, allow only one)
        repo.findByUser(user).ifPresent(b -> {
            throw new InfyPinterestException("Service.BUSINESS_ALREADY_EXISTS");
        });

        BusinessProfile bp = new BusinessProfile();
        bp.setUser(user);
        bp.setBusinessName(dto.getBusinessName());
        bp.setLogoUrl(dto.getLogoUrl());
        bp.setDescription(dto.getDescription());
        bp.setWebsiteUrl(dto.getWebsiteUrl());

        BusinessProfile saved = repo.save(bp);
        return mapper.map(saved, BusinessProfileDTO.class);
    }

    @Override
    public BusinessProfileDTO getById(Long id) {
        BusinessProfile bp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));
        return mapper.map(bp, BusinessProfileDTO.class);
    }

    @Override
    public BusinessProfileDTO getByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        BusinessProfile bp = repo.findByUser(user)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND_FOR_USER"));

        return mapper.map(bp, BusinessProfileDTO.class);
    }

    @Override
    public BusinessProfileDTO update(Long id, BusinessProfileDTO dto) {
        BusinessProfile bp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        if (dto.getBusinessName() != null) bp.setBusinessName(dto.getBusinessName());
        if (dto.getLogoUrl() != null) bp.setLogoUrl(dto.getLogoUrl());
        if (dto.getDescription() != null) bp.setDescription(dto.getDescription());
        if (dto.getWebsiteUrl() != null) bp.setWebsiteUrl(dto.getWebsiteUrl());

        BusinessProfile saved = repo.save(bp);
        return mapper.map(saved, BusinessProfileDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BusinessProfile bp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        // manual cascade: delete showcases, campaigns (and sponsored pins)
        // delete sponsored pins by business
        sponsoredRepo.deleteAllByBusiness(bp);
        // delete campaigns -> sponsored pins for campaigns already removed above by campaign delete (but we ensure)
        campaignRepo.deleteAllByBusiness(bp);
        // delete showcases
        showcaseRepo.deleteAllByBusiness(bp);

        repo.delete(bp);
    }

    @Override
    public List<BusinessProfileDTO> getAll() {
        return repo.findAll().stream()
                .map(b -> mapper.map(b, BusinessProfileDTO.class))
                .toList();
    }
}
