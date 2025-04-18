package com.aaalace.kpomini2.infrastructure.persistence;

import com.aaalace.kpomini2.domain.model.FeedingSchedule;
import com.aaalace.kpomini2.domain.repository.FeedingScheduleRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryFeedingScheduleRepository implements FeedingScheduleRepository {
    private final Map<UUID, FeedingSchedule> schedules = new ConcurrentHashMap<>();

    @Override
    public FeedingSchedule save(FeedingSchedule feedingSchedule) {
        schedules.put(feedingSchedule.getId(), feedingSchedule);
        return feedingSchedule;
    }

    @Override
    public Optional<FeedingSchedule> findById(UUID id) {
        return Optional.ofNullable(schedules.get(id));
    }

    @Override
    public List<FeedingSchedule> findAll() {
        return new ArrayList<>(schedules.values());
    }

    @Override
    public void delete(UUID id) {
        schedules.remove(id);
    }

    @Override
    public List<FeedingSchedule> findByAnimalId(UUID animalId) {
        return schedules.values().stream()
                .filter(schedule -> schedule.getAnimal().getId().equals(animalId))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedingSchedule> findByTimeRange(LocalDateTime start, LocalDateTime end) {
        return schedules.values().stream()
                .filter(schedule -> {
                    LocalDateTime time = schedule.getFeedingTime();
                    return !time.isBefore(start) && !time.isAfter(end);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedingSchedule> findPendingFeedings() {
        LocalDateTime now = LocalDateTime.now();
        return schedules.values().stream()
                .filter(schedule -> !schedule.isCompleted() && !schedule.getFeedingTime().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedingSchedule> findOverdueFeedings() {
        return schedules.values().stream()
                .filter(FeedingSchedule::isOverdue)
                .collect(Collectors.toList());
    }
} 