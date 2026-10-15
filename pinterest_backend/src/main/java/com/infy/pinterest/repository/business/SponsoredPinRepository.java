package com.infy.pinterest.repository.business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Campaign;
import com.infy.pinterest.entity.business.SponsoredPin;
import com.infy.pinterest.entity.content.Pin;

public interface SponsoredPinRepository extends JpaRepository<SponsoredPin, Long> {

    List<SponsoredPin> findByBusiness(BusinessProfile business);
    List<SponsoredPin> findByCampaign(Campaign campaign);
    List<SponsoredPin> findByPin(Pin pin);
    List<SponsoredPin> findByIsActiveTrue();

    void deleteAllByCampaign(Campaign campaign);
    void deleteAllByBusiness(BusinessProfile business);
}