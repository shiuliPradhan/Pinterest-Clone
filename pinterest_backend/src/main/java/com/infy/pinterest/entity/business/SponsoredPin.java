package com.infy.pinterest.entity.business;

import java.time.LocalDateTime;

import com.infy.pinterest.entity.content.Pin;

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
@Table(name = "sponsored_pin")
public class SponsoredPin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pin_id", nullable=false)
    private Pin pin;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="business_id", nullable=false)
    private BusinessProfile business;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="campaign_id", nullable=false)
    private Campaign campaign;

    private Boolean isActive = true;
    private Double clickRate = 0.0;

    private LocalDateTime createdAt = LocalDateTime.now();
}

