package com.infy.pinterest.repository.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.user.User;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    Optional<BusinessProfile> findByUser(User user);
    List<BusinessProfile> findAllByUser(User user);

    void deleteAllByUser(User user);
}
