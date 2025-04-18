package com.aaalace.kpomini2.domain.model;

import com.aaalace.kpomini2.domain.valueobject.FoodType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FeedingSchedule {
    private final UUID id;
    private final Animal animal;
    @Setter
    private LocalDateTime feedingTime;
    @Setter
    private FoodType foodType;
    private boolean completed;

    public FeedingSchedule(UUID id, Animal animal, LocalDateTime feedingTime, FoodType foodType) {
        this.id = id;
        this.animal = animal;
        this.feedingTime = feedingTime;
        this.foodType = foodType;
        this.completed = false;
    }

    public void markCompleted() {
        this.completed = true;
        animal.feed();
    }

    public boolean isOverdue() {
        return !completed && feedingTime.isBefore(LocalDateTime.now());
    }

    public boolean isScheduledForNow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
        LocalDateTime fiveMinutesFromNow = now.plusMinutes(5);
        
        return !completed && 
               feedingTime.isAfter(fiveMinutesAgo) && 
               feedingTime.isBefore(fiveMinutesFromNow);
    }
} 