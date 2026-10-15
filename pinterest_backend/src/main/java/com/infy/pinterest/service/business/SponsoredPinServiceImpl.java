package com.infy.pinterest.service.business;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.pinterest.dto.business.SponsoredPinDTO;
import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Campaign;
import com.infy.pinterest.entity.business.SponsoredPin;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.business.BusinessProfileRepository;
import com.infy.pinterest.repository.business.CampaignRepository;
import com.infy.pinterest.repository.business.SponsoredPinRepository;
import com.infy.pinterest.repository.content.PinRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SponsoredPinServiceImpl implements SponsoredPinService {

    private final SponsoredPinRepository repo;
    private final BusinessProfileRepository businessRepo;
    private final CampaignRepository campaignRepo;
    private final PinRepository pinRepo;
    private final ModelMapper mapper;

    @Override
    public SponsoredPinDTO create(SponsoredPinDTO dto) {
        BusinessProfile bp = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        Campaign campaign = campaignRepo.findById(dto.getCampaignId())
                .orElseThrow(() -> new InfyPinterestException("Service.CAMPAIGN_NOT_FOUND"));

        Pin pin = pinRepo.findById(dto.getPinId())
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));

        // ensure same business/campaign constraints if any (SRS)
        SponsoredPin sp = new SponsoredPin();
        sp.setBusiness(bp);
        sp.setCampaign(campaign);
        sp.setPin(pin);
        sp.setIsActive(dto.getIsActive() == null ? true : dto.getIsActive());
        sp.setClickRate(dto.getClickRate() == null ? 0.0 : dto.getClickRate());

        SponsoredPin saved = repo.save(sp);
        return mapper.map(saved, SponsoredPinDTO.class);
    }

    @Override
    public SponsoredPinDTO activate(Long id) {
        SponsoredPin sp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SPONSORED_PIN_NOT_FOUND"));
        sp.setIsActive(true);
        SponsoredPin saved = repo.save(sp);
        return mapper.map(saved, SponsoredPinDTO.class);
    }

    @Override
    public SponsoredPinDTO pause(Long id) {
        SponsoredPin sp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SPONSORED_PIN_NOT_FOUND"));
        sp.setIsActive(false);
        SponsoredPin saved = repo.save(sp);
        return mapper.map(saved, SponsoredPinDTO.class);
    }

    @Override
    public SponsoredPinDTO updateClickRate(Long id, Double rate) {
        SponsoredPin sp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SPONSORED_PIN_NOT_FOUND"));
        if (rate == null || rate < 0) {
            throw new InfyPinterestException("Service.INVALID_CLICK_RATE");
        }
        sp.setClickRate(rate);
        SponsoredPin saved = repo.save(sp);
        return mapper.map(saved, SponsoredPinDTO.class);
    }

    @Override
    public List<SponsoredPinDTO> getByCampaign(Long campaignId) {
        Campaign campaign = campaignRepo.findById(campaignId)
                .orElseThrow(() -> new InfyPinterestException("Service.CAMPAIGN_NOT_FOUND"));

        return repo.findByCampaign(campaign).stream()
                .map(x -> mapper.map(x, SponsoredPinDTO.class))
                .toList();
    }

    @Override
    public List<SponsoredPinDTO> getByBusiness(Long businessId) {
        BusinessProfile bp = businessRepo.findById(businessId)
                .orElseThrow(() -> new InfyPinterestException("Service.BUSINESS_NOT_FOUND"));

        return repo.findByBusiness(bp).stream()
                .map(x -> mapper.map(x, SponsoredPinDTO.class))
                .toList();
    }

    @Override
    public List<SponsoredPinDTO> getActive() {
        return repo.findByIsActiveTrue().stream()
                .map(x -> mapper.map(x, SponsoredPinDTO.class))
                .toList();
    }

    @Override
    public void delete(Long id) {
        SponsoredPin sp = repo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.SPONSORED_PIN_NOT_FOUND"));
        repo.delete(sp);
    }
}
