package com.infy.pinterest.service.content;

import java.util.List;

import com.infy.pinterest.dto.content.BoardDTO;

public interface BoardService {
    BoardDTO create(BoardDTO dto);
    BoardDTO update(Long id, BoardDTO dto);
    BoardDTO getById(Long id);
    List<BoardDTO> getByUser(Long userId);
    List<BoardDTO> searchByTitle(String keyword);
    void delete(Long id);
}
