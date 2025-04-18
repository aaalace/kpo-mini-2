package com.aaalace.kpomini2.domain.event;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime getOccurredOn();
} 