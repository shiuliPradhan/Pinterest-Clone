package com.infy.pinterest.entity.content;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "pin_tags")
public class PinTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;
}

