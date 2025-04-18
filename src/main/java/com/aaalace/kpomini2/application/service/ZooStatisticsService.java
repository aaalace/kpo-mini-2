package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.application.port.in.ZooStatisticsUseCase;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.model.FeedingSchedule;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.repository.EnclosureRepository;
import com.aaalace.kpomini2.domain.repository.FeedingScheduleRepository;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZooStatisticsService implements ZooStatisticsUseCase {
    
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final FeedingScheduleRepository feedingScheduleRepository;

    @Override
    public long getTotalAnimalCount() {
        return animalRepository.findAll().size();
    }

    @Override
    public long getHealthyAnimalCount() {
        return animalRepository.countHealthyAnimals();
    }

    @Override
    public long getSickAnimalCount() {
        return animalRepository.countSickAnimals();
    }

    @Override
    public Map<AnimalType, Long> getAnimalCountByType() {
        return animalRepository.findAll().stream()
                .collect(Collectors.groupingBy(Animal::getType, Collectors.counting()));
    }

    @Override
    public long getTotalEnclosureCount() {
        return enclosureRepository.findAll().size();
    }

    @Override
    public long getAvailableEnclosureCount() {
        return enclosureRepository.findAvailable().size();
    }

    @Override
    public long getOccupiedEnclosureCount() {
        return getTotalEnclosureCount() - getAvailableEnclosureCount();
    }

    @Override
    public Map<EnclosureType, Long> getEnclosureCountByType() {
        Map<EnclosureType, Long> countByType = enclosureRepository.findAll().stream()
                .collect(Collectors.groupingBy(Enclosure::getType, Collectors.counting()));
        
        
        Arrays.stream(EnclosureType.values())
                .forEach(type -> countByType.putIfAbsent(type, 0L));
        
        return countByType;
    }

    @Override
    public long getTotalScheduledFeedings() {
        return feedingScheduleRepository.findAll().size();
    }

    @Override
    public long getCompletedFeedingsCount() {
        return feedingScheduleRepository.findAll().stream()
                .filter(FeedingSchedule::isCompleted)
                .count();
    }

    @Override
    public long getPendingFeedingsCount() {
        return feedingScheduleRepository.findPendingFeedings().size();
    }

    @Override
    public long getOverdueFeedingsCount() {
        return feedingScheduleRepository.findOverdueFeedings().size();
    }
} 