package com.infy.pinterest.dto.business;

import lombok.Data;

@Data
public class ShowcaseDTO {
    private Long id;
    private Long businessId;
    private String title;
    private String description;
    private String coverImageUrl;
}
