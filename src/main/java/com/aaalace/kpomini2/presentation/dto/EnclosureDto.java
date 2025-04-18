package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnclosureDto {
    private UUID id;
    private EnclosureType type;
    private int maxCapacity;
    private int currentOccupancy;
    private boolean isCleaned;
    private List<UUID> animalIds;
} 