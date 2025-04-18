package com.aaalace.kpomini2.infrastructure.persistence;

import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAnimalRepositoryTest {

    private InMemoryAnimalRepository repository;
    private Animal testAnimal1;
    private Animal testAnimal2;
    private UUID animal1Id;
    private UUID animal2Id;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAnimalRepository();
        
        animal1Id = UUID.randomUUID();
        testAnimal1 = new Animal(
                animal1Id,
                AnimalType.PREDATOR,
                "Лев",
                LocalDate.of(2019, 5, 10),
                Gender.MALE,
                FoodType.MEAT
        );
        
        animal2Id = UUID.randomUUID();
        testAnimal2 = new Animal(
                animal2Id,
                AnimalType.HERBIVORE,
                "Зебра",
                LocalDate.of(2020, 3, 15),
                Gender.FEMALE,
                FoodType.GRASS
        );

        repository.save(testAnimal1);
        repository.save(testAnimal2);

        testAnimal2.makeIll();
        repository.save(testAnimal2);
    }

    @Test
    void findById_ExistingAnimal_ShouldReturnAnimal() {
        Optional<Animal> result = repository.findById(animal1Id);

        assertTrue(result.isPresent());
        assertEquals(testAnimal1, result.get());
    }
    
    @Test
    void findById_NonExistingAnimal_ShouldReturnEmpty() {
        Optional<Animal> result = repository.findById(UUID.randomUUID());

        assertFalse(result.isPresent());
    }
    
    @Test
    void findAll_ShouldReturnAllAnimals() {
        List<Animal> animals = repository.findAll();

        assertEquals(2, animals.size());
        assertTrue(animals.contains(testAnimal1));
        assertTrue(animals.contains(testAnimal2));
    }
    
    @Test
    void delete_ExistingAnimal_ShouldRemoveAnimal() {
        repository.delete(animal1Id);

        Optional<Animal> result = repository.findById(animal1Id);
        assertFalse(result.isPresent());
        assertEquals(1, repository.findAll().size());
    }
    
    @Test
    void findByEnclosureId_ShouldReturnAnimalsInEnclosure() {
        Enclosure enclosure = new Enclosure(UUID.randomUUID(), EnclosureType.PREDATOR_ENCLOSURE, 5);
        testAnimal1.setEnclosure(enclosure);
        repository.save(testAnimal1);

        List<Animal> result = repository.findByEnclosureId(enclosure.getId());

        assertEquals(1, result.size());
        assertEquals(testAnimal1, result.get(0));
    }
    
    @Test
    void countHealthyAnimals_ShouldReturnCorrectCount() {
        long healthyCount = repository.countHealthyAnimals();

        assertEquals(1, healthyCount);
    }
    
    @Test
    void countSickAnimals_ShouldReturnCorrectCount() {
        long sickCount = repository.countSickAnimals();

        assertEquals(1, sickCount);
    }
} 