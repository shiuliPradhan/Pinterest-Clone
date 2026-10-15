package com.infy.pinterest.dto.business;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CampaignDTO {
    private Long id;
    private Long businessId;
    private String campaignName;
    private String campaignDescription;
    private Double budget;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}

