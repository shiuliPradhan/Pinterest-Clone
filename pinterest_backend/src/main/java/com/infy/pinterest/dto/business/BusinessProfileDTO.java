package com.infy.pinterest.dto.business;

import lombok.Data;

@Data
public class BusinessProfileDTO {
    private Long id;
    private Long userId;
    private String businessName;
    private String logoUrl;
    private String description;
    private String websiteUrl;
}

