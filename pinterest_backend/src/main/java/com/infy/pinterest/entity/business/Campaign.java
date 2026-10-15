package com.infy.pinterest.entity.business;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Table(name = "campaign")
public class Campaign {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="business_id", nullable=false)
    private BusinessProfile business;

    private String campaignName;
    private String campaignDescription;
    private Double budget;

    private LocalDate startDate;
    private LocalDate endDate;

    private String status = "ACTIVE";
    private LocalDateTime createdAt = LocalDateTime.now();
}

