package com.infy.pinterest.repository.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinMedia;

public interface PinMediaRepository extends JpaRepository<PinMedia, Long> {
    List<PinMedia> findByPin(Pin pin);
    void deleteAllByPin(Pin pin);
}