package com.infy.pinterest.repository.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinSave;
import com.infy.pinterest.entity.user.User;

public interface PinSaveRepository extends JpaRepository<PinSave, Long> {
    List<PinSave> findByUser(User user);
    List<PinSave> findByPin(Pin pin);
    List<PinSave> findByBoard(Board board);

    void deleteAllByPin(Pin pin);
}
