package com.infy.pinterest.dto.content;

import lombok.Data;

@Data
public class PinMediaDTO {
    private Long id;
    private Long pinId;
    private String mediaUrl;
    private String mediaType;
    private Integer ord;
}

