package com.aaalace.kpomini2.application.port.in;

import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AnimalManagementUseCase {
    UUID createAnimal(AnimalType type, String name, LocalDate birthDate, Gender gender, FoodType favoriteFood);
    void removeAnimal(UUID animalId);
    Animal getAnimal(UUID animalId);
    List<Animal> getAllAnimals();
    void healAnimal(UUID animalId);
    void setAnimalIll(UUID animalId);
    void updateAnimalName(UUID animalId, String newName);
    void updateAnimalFavoriteFood(UUID animalId, FoodType newFavoriteFood);
} 