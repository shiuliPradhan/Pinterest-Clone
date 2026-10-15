package com.infy.pinterest.repository.content;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.PinTag;

public interface PinTagRepository extends JpaRepository<PinTag, Long> {
    Optional<PinTag> findByTag(String tag);
}
