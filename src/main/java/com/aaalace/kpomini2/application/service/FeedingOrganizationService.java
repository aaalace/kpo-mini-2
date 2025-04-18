package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.application.port.in.FeedingManagementUseCase;
import com.aaalace.kpomini2.domain.event.FeedingTimeEvent;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.FeedingSchedule;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.repository.FeedingScheduleRepository;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedingOrganizationService implements FeedingManagementUseCase {
    
    private final FeedingScheduleRepository feedingScheduleRepository;
    private final AnimalRepository animalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UUID scheduleFeedingForAnimal(UUID animalId, LocalDateTime feedingTime, FoodType foodType) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Животное с id " + animalId + " не найдено"));
        
        FeedingSchedule feedingSchedule = new FeedingSchedule(UUID.randomUUID(), animal, feedingTime, foodType);
        FeedingSchedule savedSchedule = feedingScheduleRepository.save(feedingSchedule);
        
        return savedSchedule.getId();
    }

    @Override
    public void markFeedingAsCompleted(UUID feedingId) {
        FeedingSchedule feedingSchedule = getFeedingDetails(feedingId);
        feedingSchedule.markCompleted();
        feedingScheduleRepository.save(feedingSchedule);
    }

    @Override
    public List<FeedingSchedule> getAllFeedings() {
        return feedingScheduleRepository.findAll();
    }

    @Override
    public List<FeedingSchedule> getFeedingsForAnimal(UUID animalId) {
        
        animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Животное с id " + animalId + " не найдено"));
        
        return feedingScheduleRepository.findByAnimalId(animalId);
    }

    @Override
    public List<FeedingSchedule> getFeedingsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return feedingScheduleRepository.findByTimeRange(start, end);
    }

    @Override
    public List<FeedingSchedule> getPendingFeedings() {
        return feedingScheduleRepository.findPendingFeedings();
    }

    @Override
    public List<FeedingSchedule> getOverdueFeedings() {
        return feedingScheduleRepository.findOverdueFeedings();
    }

    @Override
    public FeedingSchedule getFeedingDetails(UUID feedingId) {
        return feedingScheduleRepository.findById(feedingId)
                .orElseThrow(() -> new IllegalArgumentException("Расписание кормления с id " + feedingId + " не найдено"));
    }

    @Override
    public void removeFeedingSchedule(UUID feedingId) {
        feedingScheduleRepository.delete(feedingId);
    }
    
    
    @Scheduled(fixedRate = 300000) 
    public void checkFeedingSchedule() {
        List<FeedingSchedule> feedingsForNow = getAllFeedings().stream()
                .filter(FeedingSchedule::isScheduledForNow)
                .toList();
        
        for (FeedingSchedule feeding : feedingsForNow) {
            eventPublisher.publishEvent(new FeedingTimeEvent(UUID.randomUUID(), feeding));
        }
    }
} 