package com.aaalace.kpomini2.domain.event;

import com.aaalace.kpomini2.domain.model.FeedingSchedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FeedingTimeEvent implements DomainEvent {
    private final UUID id;
    private final FeedingSchedule feedingSchedule;
    private final LocalDateTime occurredOn;

    public FeedingTimeEvent(UUID id, FeedingSchedule feedingSchedule) {
        this.id = id;
        this.feedingSchedule = feedingSchedule;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
} 