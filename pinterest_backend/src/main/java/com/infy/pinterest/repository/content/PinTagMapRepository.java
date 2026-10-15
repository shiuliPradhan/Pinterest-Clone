package com.infy.pinterest.repository.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinTag;
import com.infy.pinterest.entity.content.PinTagMap;

public interface PinTagMapRepository extends JpaRepository<PinTagMap, Long> {
    List<PinTagMap> findByPin(Pin pin);
    List<PinTagMap> findByTag(PinTag tag);

    void deleteAllByPin(Pin pin);
}
