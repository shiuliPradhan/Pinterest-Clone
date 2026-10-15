package com.infy.pinterest.dto.content;

import lombok.Data;

@Data
public class PinDTO {
    private Long id;
    private Long boardId;
    private Long userId;
    private String title;
    private String description;
    private String sourceUrl;
    private Boolean isPrivate;
    private String status;
}

