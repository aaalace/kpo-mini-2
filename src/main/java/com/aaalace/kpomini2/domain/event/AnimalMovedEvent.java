package com.aaalace.kpomini2.domain.event;

import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.Enclosure;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AnimalMovedEvent implements DomainEvent {
    private final UUID id;
    private final Animal animal;
    private final Enclosure sourceEnclosure;
    private final Enclosure targetEnclosure;
    private final LocalDateTime occurredOn;

    public AnimalMovedEvent(UUID id, Animal animal, Enclosure sourceEnclosure, Enclosure targetEnclosure) {
        this.id = id;
        this.animal = animal;
        this.sourceEnclosure = sourceEnclosure;
        this.targetEnclosure = targetEnclosure;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
} 