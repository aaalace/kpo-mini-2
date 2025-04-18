package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.domain.event.AnimalMovedEvent;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.repository.EnclosureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimalTransferServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private EnclosureRepository enclosureRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private AnimalTransferService animalTransferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        animalTransferService = new AnimalTransferService(animalRepository, enclosureRepository, eventPublisher);
    }

    @Test
    void transferAnimal_ShouldTransferAnimalToNewEnclosure() {
        
        UUID animalId = UUID.randomUUID();
        UUID targetEnclosureId = UUID.randomUUID();
        
        Animal animal = mock(Animal.class);
        Enclosure sourceEnclosure = mock(Enclosure.class);
        Enclosure targetEnclosure = mock(Enclosure.class);
        
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(animal));
        when(enclosureRepository.findById(targetEnclosureId)).thenReturn(Optional.of(targetEnclosure));
        when(animal.getCurrentEnclosure()).thenReturn(sourceEnclosure);
        
        ArgumentCaptor<AnimalMovedEvent> eventCaptor = ArgumentCaptor.forClass(AnimalMovedEvent.class);
        
        
        animalTransferService.transferAnimal(animalId, targetEnclosureId);
        
        
        verify(sourceEnclosure, times(1)).removeAnimal(animal);
        verify(targetEnclosure, times(1)).addAnimal(animal);
        verify(enclosureRepository, times(1)).save(sourceEnclosure);
        verify(enclosureRepository, times(1)).save(targetEnclosure);
        verify(animalRepository, times(1)).save(animal);
        
        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        
        AnimalMovedEvent capturedEvent = eventCaptor.getValue();
        assertNotNull(capturedEvent);
        assertEquals(animal, capturedEvent.getAnimal());
        assertEquals(sourceEnclosure, capturedEvent.getSourceEnclosure());
        assertEquals(targetEnclosure, capturedEvent.getTargetEnclosure());
    }
} 