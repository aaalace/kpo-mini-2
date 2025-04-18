package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimalManagementServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    private AnimalManagementService animalManagementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        animalManagementService = new AnimalManagementService(animalRepository);
    }

    @Test
    void createAnimal_ShouldCreateAndSaveAnimal() {
        
        AnimalType type = AnimalType.PREDATOR;
        String name = "Лев";
        LocalDate birthDate = LocalDate.of(2020, 1, 1);
        Gender gender = Gender.MALE;
        FoodType favoriteFood = FoodType.MEAT;

        ArgumentCaptor<Animal> animalCaptor = ArgumentCaptor.forClass(Animal.class);
        when(animalRepository.save(animalCaptor.capture())).thenReturn(new Animal(UUID.randomUUID(), type, name, birthDate, gender, favoriteFood));

        
        UUID animalId = animalManagementService.createAnimal(type, name, birthDate, gender, favoriteFood);

        
        verify(animalRepository, times(1)).save(any(Animal.class));
        
        Animal capturedAnimal = animalCaptor.getValue();
        assertNotNull(capturedAnimal);
        assertEquals(type, capturedAnimal.getType());
        assertEquals(name, capturedAnimal.getName());
        assertEquals(birthDate, capturedAnimal.getBirthDate());
        assertEquals(gender, capturedAnimal.getGender());
        assertEquals(favoriteFood, capturedAnimal.getFavoriteFood());
        assertTrue(capturedAnimal.isHealthy());
    }

    @Test
    void healAnimal_ShouldSetAnimalHealthStatusToTrue() {
        
        UUID animalId = UUID.randomUUID();
        Animal animal = mock(Animal.class);
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(animal));
        when(animalRepository.save(animal)).thenReturn(animal);

        
        animalManagementService.healAnimal(animalId);

        
        verify(animal, times(1)).heal();
        verify(animalRepository, times(1)).save(animal);
    }
} 