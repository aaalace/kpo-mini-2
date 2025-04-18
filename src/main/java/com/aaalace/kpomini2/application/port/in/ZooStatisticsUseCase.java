package com.aaalace.kpomini2.application.port.in;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;

import java.util.Map;

public interface ZooStatisticsUseCase {
    long getTotalAnimalCount();
    long getHealthyAnimalCount();
    long getSickAnimalCount();
    Map<AnimalType, Long> getAnimalCountByType();
    
    long getTotalEnclosureCount();
    long getAvailableEnclosureCount();
    long getOccupiedEnclosureCount();
    Map<EnclosureType, Long> getEnclosureCountByType();
    
    long getTotalScheduledFeedings();
    long getCompletedFeedingsCount();
    long getPendingFeedingsCount();
    long getOverdueFeedingsCount();
} 