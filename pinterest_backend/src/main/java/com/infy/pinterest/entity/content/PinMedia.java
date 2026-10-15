package com.infy.pinterest.entity.content;


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
@Table(name = "pin_media")
public class PinMedia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pin_id", nullable=false)
    private Pin pin;

    private String mediaUrl;
    private String mediaType; // IMAGE / VIDEO
    private Integer ord;
}

