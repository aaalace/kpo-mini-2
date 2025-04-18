package com.aaalace.kpomini2.domain.model;

import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Enclosure {
    private final UUID id;
    private final EnclosureType type;
    private final int maxCapacity;
    private final List<Animal> animals;
    private boolean isCleaned;

    public Enclosure(UUID id, EnclosureType type, int maxCapacity) {
        this.id = id;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.animals = new ArrayList<>();
        this.isCleaned = true;
    }

    public int getCurrentOccupancy() {
        return animals.size();
    }

    public boolean hasSpace() {
        return getCurrentOccupancy() < maxCapacity;
    }

    public void addAnimal(Animal animal) {
        if (!hasSpace()) {
            throw new IllegalStateException("Вольер переполнен");
        }
        
        
        if (!isCompatibleWithAnimal(animal)) {
            throw new IllegalArgumentException("Животное не совместимо с этим типом вольера");
        }
        
        animals.add(animal);
        animal.setEnclosure(this);
    }

    public void removeAnimal(Animal animal) {
        if (animals.remove(animal)) {
            System.out.println("Животное " + animal.getName() + " удалено из вольера");
        } else {
            throw new IllegalArgumentException("Животное не найдено в вольере");
        }
    }

    public void clean() {
        this.isCleaned = true;
        System.out.println("Вольер " + id + " очищен");
    }

    private boolean isCompatibleWithAnimal(Animal animal) {
        return switch (animal.getType()) {
            case PREDATOR -> type == EnclosureType.PREDATOR_ENCLOSURE;
            case HERBIVORE -> type == EnclosureType.HERBIVORE_ENCLOSURE;
            case BIRD -> type == EnclosureType.BIRD_AVIARY;
            case AQUATIC -> type == EnclosureType.AQUARIUM;
            case REPTILE -> type == EnclosureType.REPTILE_TERRARIUM;
            case AMPHIBIAN -> type == EnclosureType.AMPHIBIAN_TERRARIUM;
        };
    }
} 