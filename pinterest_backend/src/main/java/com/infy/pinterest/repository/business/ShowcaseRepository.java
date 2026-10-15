package com.infy.pinterest.repository.business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Showcase;

public interface ShowcaseRepository extends JpaRepository<Showcase, Long> {
    List<Showcase> findByBusiness(BusinessProfile business);
    void deleteAllByBusiness(BusinessProfile business);
}