package com.infy.pinterest.service.business;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.pinterest.dto.business.ShowcaseDTO;
import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Showcase;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.business.BusinessProfileRepository;
import com.infy.pinterest.repository.business.ShowcaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowcaseServiceImpl implements ShowcaseService {

    private final ShowcaseRepository repo;
    private final BusinessProfileRepository businessRepo;
    private final ModelMapper mapper;

    @Override
    public ShowcaseDTO create(ShowcaseDTO dto) {
        BusinessProfile bp = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        Showcase sc = new Showcase();
        sc.setBusiness(bp);
        sc.setTitle(dto.getTitle());
        sc.setDescription(dto.getDescription());
        sc.setCoverImageUrl(dto.getCoverImageUrl());

        Showcase saved = repo.save(sc);
        return mapper.map(saved, ShowcaseDTO.class);
    }

    @Override
    public ShowcaseDTO getById(Long id) {
        Showcase sc = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SHOWCASE_NOT_FOUND"));
        return mapper.map(sc, ShowcaseDTO.class);
    }

    @Override
    public ShowcaseDTO update(Long id, ShowcaseDTO dto) {
        Showcase sc = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SHOWCASE_NOT_FOUND"));

        if (dto.getTitle() != null) sc.setTitle(dto.getTitle());
        if (dto.getDescription() != null) sc.setDescription(dto.getDescription());
        if (dto.getCoverImageUrl() != null) sc.setCoverImageUrl(dto.getCoverImageUrl());

        Showcase saved = repo.save(sc);
        return mapper.map(saved, ShowcaseDTO.class);
    }

    @Override
    public void delete(Long id) {
        Showcase sc = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SHOWCASE_NOT_FOUND"));
        repo.delete(sc);
    }

    @Override
    public List<ShowcaseDTO> getByBusiness(Long businessId) {
        BusinessProfile bp = businessRepo.findById(businessId)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        return repo.findByBusiness(bp).stream()
                .map(s -> mapper.map(s, ShowcaseDTO.class))
                .toList();
    }
}
