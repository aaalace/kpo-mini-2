package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZooStatisticsDto {
    private long totalAnimalCount;
    private long healthyAnimalCount;
    private long sickAnimalCount;
    private Map<AnimalType, Long> animalCountByType;
    
    private long totalEnclosureCount;
    private long availableEnclosureCount;
    private long occupiedEnclosureCount;
    private Map<EnclosureType, Long> enclosureCountByType;
    
    private long totalScheduledFeedings;
    private long completedFeedingsCount;
    private long pendingFeedingsCount;
    private long overdueFeedingsCount;
} 