package com.infy.pinterest.dto.content;

import lombok.Data;

@Data
public class BoardDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Boolean isPrivate;
    private String coverImageUrl;
}


