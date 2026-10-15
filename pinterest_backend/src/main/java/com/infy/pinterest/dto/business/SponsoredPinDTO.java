package com.infy.pinterest.dto.business;

import lombok.Data;

@Data
public class SponsoredPinDTO {
    private Long id;
    private Long pinId;
    private Long businessId;
    private Long campaignId;
    private Boolean isActive;
    private Double clickRate;
}
