package com.aaalace.kpomini2.domain.model;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void createAnimal_ShouldCreateHealthyAnimal() {
        
        UUID id = UUID.randomUUID();
        AnimalType type = AnimalType.PREDATOR;
        String name = "Тигр";
        LocalDate birthDate = LocalDate.of(2018, 5, 15);
        Gender gender = Gender.FEMALE;
        FoodType favoriteFood = FoodType.MEAT;
        
        
        Animal animal = new Animal(id, type, name, birthDate, gender, favoriteFood);
        
        
        assertEquals(id, animal.getId());
        assertEquals(type, animal.getType());
        assertEquals(name, animal.getName());
        assertEquals(birthDate, animal.getBirthDate());
        assertEquals(gender, animal.getGender());
        assertEquals(favoriteFood, animal.getFavoriteFood());
        assertTrue(animal.isHealthy());
        assertNull(animal.getCurrentEnclosure());
    }
    
    @Test
    void setEnclosure_WithCompatibleEnclosure_ShouldSetEnclosure() {
        
        Animal predator = new Animal(
                UUID.randomUUID(),
                AnimalType.PREDATOR,
                "Лев",
                LocalDate.now().minusYears(5),
                Gender.MALE,
                FoodType.MEAT
        );
        
        Enclosure predatorEnclosure = new Enclosure(
                UUID.randomUUID(),
                EnclosureType.PREDATOR_ENCLOSURE,
                5
        );
        
        
        predator.setEnclosure(predatorEnclosure);
        
        
        assertEquals(predatorEnclosure, predator.getCurrentEnclosure());
    }
    
    @Test
    void setEnclosure_WithIncompatibleEnclosure_ShouldThrowException() {
        
        Animal predator = new Animal(
                UUID.randomUUID(),
                AnimalType.PREDATOR,
                "Лев",
                LocalDate.now().minusYears(5),
                Gender.MALE,
                FoodType.MEAT
        );
        
        Enclosure herbivoreEnclosure = new Enclosure(
                UUID.randomUUID(),
                EnclosureType.HERBIVORE_ENCLOSURE,
                5
        );
        
        
        assertThrows(IllegalArgumentException.class, () -> predator.setEnclosure(herbivoreEnclosure));
    }
} 