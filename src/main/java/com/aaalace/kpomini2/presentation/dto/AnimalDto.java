package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    private UUID id;
    private AnimalType type;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private FoodType favoriteFood;
    private boolean healthy;
    private UUID currentEnclosureId;
} 