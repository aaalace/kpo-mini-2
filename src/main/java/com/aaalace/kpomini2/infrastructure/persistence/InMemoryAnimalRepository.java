package com.aaalace.kpomini2.infrastructure.persistence;

import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<UUID, Animal> animals = new ConcurrentHashMap<>();

    @Override
    public Animal save(Animal animal) {
        animals.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public Optional<Animal> findById(UUID id) {
        return Optional.ofNullable(animals.get(id));
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public void delete(UUID id) {
        animals.remove(id);
    }

    @Override
    public List<Animal> findByEnclosureId(UUID enclosureId) {
        return animals.values().stream()
                .filter(animal -> animal.getCurrentEnclosure() != null && 
                        animal.getCurrentEnclosure().getId().equals(enclosureId))
                .collect(Collectors.toList());
    }

    @Override
    public long countHealthyAnimals() {
        return animals.values().stream()
                .filter(Animal::isHealthy)
                .count();
    }

    @Override
    public long countSickAnimals() {
        return animals.values().stream()
                .filter(animal -> !animal.isHealthy())
                .count();
    }
} 