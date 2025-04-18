package com.aaalace.kpomini2.domain.repository;

import com.aaalace.kpomini2.domain.model.FeedingSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedingScheduleRepository {
    FeedingSchedule save(FeedingSchedule feedingSchedule);
    Optional<FeedingSchedule> findById(UUID id);
    List<FeedingSchedule> findAll();
    void delete(UUID id);
    List<FeedingSchedule> findByAnimalId(UUID animalId);
    List<FeedingSchedule> findByTimeRange(LocalDateTime start, LocalDateTime end);
    List<FeedingSchedule> findPendingFeedings();
    List<FeedingSchedule> findOverdueFeedings();
} 