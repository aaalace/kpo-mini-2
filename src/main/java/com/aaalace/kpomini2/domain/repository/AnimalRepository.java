package com.aaalace.kpomini2.domain.repository;

import com.aaalace.kpomini2.domain.model.Animal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalRepository {
    Animal save(Animal animal);
    Optional<Animal> findById(UUID id);
    List<Animal> findAll();
    void delete(UUID id);
    List<Animal> findByEnclosureId(UUID enclosureId);
    long countHealthyAnimals();
    long countSickAnimals();
} 