package com.infy.pinterest.repository.business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByBusiness(BusinessProfile business);

    void deleteAllByBusiness(BusinessProfile business);
}
