package com.aaalace.kpomini2.application.port.in;

import com.aaalace.kpomini2.domain.model.FeedingSchedule;
import com.aaalace.kpomini2.domain.valueobject.FoodType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FeedingManagementUseCase {
    UUID scheduleFeedingForAnimal(UUID animalId, LocalDateTime feedingTime, FoodType foodType);
    void markFeedingAsCompleted(UUID feedingId);
    List<FeedingSchedule> getAllFeedings();
    List<FeedingSchedule> getFeedingsForAnimal(UUID animalId);
    List<FeedingSchedule> getFeedingsInTimeRange(LocalDateTime start, LocalDateTime end);
    List<FeedingSchedule> getPendingFeedings();
    List<FeedingSchedule> getOverdueFeedings();
    FeedingSchedule getFeedingDetails(UUID feedingId);
    void removeFeedingSchedule(UUID feedingId);
} 