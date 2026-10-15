package com.infy.pinterest.dto.content;

import lombok.Data;

@Data
public class PinSaveDTO {
    private Long id;
    private Long pinId;
    private Long userId;
    private Long boardId;   // nullable
}
