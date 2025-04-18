package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.application.port.in.AnimalTransferUseCase;
import com.aaalace.kpomini2.domain.event.AnimalMovedEvent;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.repository.EnclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalTransferService implements AnimalTransferUseCase {
    
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void transferAnimal(UUID animalId, UUID targetEnclosureId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Животное с id " + animalId + " не найдено"));
        
        Enclosure targetEnclosure = enclosureRepository.findById(targetEnclosureId)
                .orElseThrow(() -> new IllegalArgumentException("Вольер с id " + targetEnclosureId + " не найден"));
        
        Enclosure sourceEnclosure = animal.getCurrentEnclosure();
        
        if (sourceEnclosure != null) {
            sourceEnclosure.removeAnimal(animal);
            enclosureRepository.save(sourceEnclosure);
        }
        
        targetEnclosure.addAnimal(animal);
        enclosureRepository.save(targetEnclosure);
        animalRepository.save(animal);

        AnimalMovedEvent event = new AnimalMovedEvent(
                UUID.randomUUID(),
                animal,
                sourceEnclosure,
                targetEnclosure
        );
        
        eventPublisher.publishEvent(event);
    }
} 