package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.FoodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedingScheduleDto {
    private UUID id;
    private UUID animalId;
    private String animalName;
    private LocalDateTime feedingTime;
    private FoodType foodType;
    private boolean completed;
    private boolean overdue;
} 