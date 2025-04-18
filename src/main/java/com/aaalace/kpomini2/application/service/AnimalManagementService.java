package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.application.port.in.AnimalManagementUseCase;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalManagementService implements AnimalManagementUseCase {
    
    private final AnimalRepository animalRepository;

    @Override
    public UUID createAnimal(AnimalType type, String name, LocalDate birthDate, Gender gender, FoodType favoriteFood) {
        Animal animal = new Animal(UUID.randomUUID(), type, name, birthDate, gender, favoriteFood);
        Animal savedAnimal = animalRepository.save(animal);
        return savedAnimal.getId();
    }

    @Override
    public void removeAnimal(UUID animalId) {
        animalRepository.delete(animalId);
    }

    @Override
    public Animal getAnimal(UUID animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Животное с id " + animalId + " не найдено"));
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public void healAnimal(UUID animalId) {
        Animal animal = getAnimal(animalId);
        animal.heal();
        animalRepository.save(animal);
    }

    @Override
    public void setAnimalIll(UUID animalId) {
        Animal animal = getAnimal(animalId);
        animal.makeIll();
        animalRepository.save(animal);
    }

    @Override
    public void updateAnimalName(UUID animalId, String newName) {
        Animal animal = getAnimal(animalId);
        animal.changeName(newName);
        animalRepository.save(animal);
    }

    @Override
    public void updateAnimalFavoriteFood(UUID animalId, FoodType newFavoriteFood) {
        Animal animal = getAnimal(animalId);
        animal.setFavoriteFood(newFavoriteFood);
        animalRepository.save(animal);
    }
} 